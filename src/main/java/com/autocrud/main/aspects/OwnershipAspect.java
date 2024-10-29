package com.autocrud.main.aspects;

import com.autocrud.main.annotations.CheckOwnership;
import com.autocrud.main.exceptions.custom.UnauthorizedException;
import com.autocrud.main.services.AuthService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.autocrud.main.services.ChannelService;
import com.autocrud.main.services.EntryService;
import com.autocrud.main.services.FieldService;

@Aspect
@Component
public class OwnershipAspect {

    private final AuthService jwtTokenService;
    private final ChannelService channelService;
    private final FieldService fieldService;
    private final EntryService entryService;

    public OwnershipAspect(AuthService jwtTokenService, ChannelService channelService, 
                           FieldService fieldService, EntryService entryService) {
        this.jwtTokenService = jwtTokenService;
        this.channelService = channelService;
        this.fieldService = fieldService;
        this.entryService = entryService;
    }

    @Before("@annotation(checkOwnership) && args(..)")
    public void checkOwnership(JoinPoint joinPoint, CheckOwnership checkOwnership) {
        String resourceType = checkOwnership.resourceType();
        String resourceIdParamName = checkOwnership.resourceId();
    
        String username = getCurrentUsername();
        if (username == null) {
            throw new UnauthorizedException("User not authenticated.");
        }
    
        Long resourceId = getResourceId(joinPoint, resourceIdParamName);
    
        Long currentUserId = jwtTokenService.getUserIdFromToken(username);
    
        // Check ownership based on the resource type
        switch (resourceType.toLowerCase()) {
            case "channel":
                checkChannelOwnership(resourceId, currentUserId);
                break;
            case "field":
                checkFieldOwnership(resourceId, currentUserId);
                break;
            case "entry":
                checkEntryOwnership(resourceId, currentUserId);
                break;
            default:
                throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }
    }
    
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
    
    private Long getResourceId(JoinPoint joinPoint, String resourceIdParamName) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
    
        // Find the parameter index of the resource ID
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(resourceIdParamName)) {
                return (Long) args[i];
            }
        }
        throw new IllegalArgumentException("Resource ID parameter '" + resourceIdParamName + "' not found.");
    }
    
    
    private void checkChannelOwnership(Long channelId, Long currentUserId) {
        Long ownerId = channelService.getOwnerIdByChannelId(channelId);
        if (!ownerId.equals(currentUserId)) {
            throw new UnauthorizedException("You do not own this channel.");
        }
    }
    
    private void checkFieldOwnership(Long fieldId, Long currentUserId) {
        Long channelId = fieldService.getChannelIdByFieldId(fieldId);
        Long ownerId = channelService.getOwnerIdByChannelId(channelId);
        if (!ownerId.equals(currentUserId)) {
            throw new UnauthorizedException("You do not own this field.");
        }
    }

    private void checkEntryOwnership(Long entryId, Long currentUserId) {
        Long channelId = entryService.getChannelIdByEntryId(entryId);
        Long ownerId = channelService.getOwnerIdByChannelId(channelId);
        if (!ownerId.equals(currentUserId)) {
            throw new UnauthorizedException("You do not own this entry.");
        }
    }
}
