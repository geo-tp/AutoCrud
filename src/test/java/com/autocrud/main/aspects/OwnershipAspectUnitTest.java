package com.autocrud.main.aspects;

import com.autocrud.main.annotations.CheckOwnership;
import com.autocrud.main.exceptions.custom.UnauthorizedException;
import com.autocrud.main.services.AuthService;
import com.autocrud.main.services.ChannelService;
import com.autocrud.main.services.EntryService;
import com.autocrud.main.services.FieldService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OwnershipAspectUnitTest {

    @Mock
    private AuthService authService;

    @Mock
    private ChannelService channelService;

    @Mock
    private FieldService fieldService;

    @Mock
    private EntryService entryService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private OwnershipAspect ownershipAspect;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Set up method signature in the join point
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getParameterNames()).thenReturn(new String[]{"resourceId"});
        when(joinPoint.getArgs()).thenReturn(new Object[]{1L});

        // Set up a mocked request context
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testCheckOwnershipWithValidTokenAndOwnership() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(authService.getUserIdFromToken("validToken")).thenReturn(1L);
        when(channelService.getOwnerIdByChannelId(1L)).thenReturn(1L);

        CheckOwnership annotation = mock(CheckOwnership.class);
        when(annotation.resourceType()).thenReturn("channel");
        when(annotation.resourceId()).thenReturn("resourceId");

        assertDoesNotThrow(() -> ownershipAspect.checkOwnership(joinPoint, annotation));
    }

    @Test
    void testCheckOwnershipWithoutToken() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        CheckOwnership annotation = mock(CheckOwnership.class);
        when(annotation.resourceType()).thenReturn("channel");
        when(annotation.resourceId()).thenReturn("resourceId");

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> ownershipAspect.checkOwnership(joinPoint, annotation));
        assertEquals("JWT token not found in the request headers.", exception.getMessage());
    }

    @Test
    void testCheckOwnershipWithInvalidOwnership() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(authService.getUserIdFromToken("validToken")).thenReturn(2L); // Different user ID
        when(channelService.getOwnerIdByChannelId(1L)).thenReturn(1L); // Channel owned by user ID 1

        CheckOwnership annotation = mock(CheckOwnership.class);
        when(annotation.resourceType()).thenReturn("channel");
        when(annotation.resourceId()).thenReturn("resourceId");

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> ownershipAspect.checkOwnership(joinPoint, annotation));
        assertEquals("You do not own this channel.", exception.getMessage());
    }

    @Test
    void testCheckOwnershipWithInvalidTokenFormat() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidToken");

        CheckOwnership annotation = mock(CheckOwnership.class);
        when(annotation.resourceType()).thenReturn("channel");
        when(annotation.resourceId()).thenReturn("resourceId");

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> ownershipAspect.checkOwnership(joinPoint, annotation));
        assertEquals("JWT token not found in the request headers.", exception.getMessage());
    }
}
