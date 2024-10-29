package com.autocrud.main.services;

import com.autocrud.main.dtos.ChannelDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.User;
import com.autocrud.main.exceptions.custom.ChannelNotFoundException;
import com.autocrud.main.exceptions.custom.UserNotFoundException;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.repositories.UserRepository;
import com.autocrud.main.transformers.ChannelTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChannelServiceUnitTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FieldService fieldService;

    @Mock
    private ChannelTransformer channelTransformer;

    @InjectMocks
    private ChannelService channelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateChannelFromDTO_Success() {
        Long ownerId = 1L;
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setOwnerId(ownerId);

        User owner = new User();
        owner.setId(ownerId);

        Channel channel = new Channel();
        Channel savedChannel = new Channel();
        ChannelDTO savedChannelDTO = new ChannelDTO();

        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(channelTransformer.convertToEntity(channelDTO, owner)).thenReturn(channel);
        when(channelRepository.save(channel)).thenReturn(savedChannel);
        when(channelTransformer.convertToDTO(savedChannel)).thenReturn(savedChannelDTO);

        ChannelDTO result = channelService.createChannelFromDTO(channelDTO);

        assertNotNull(result);
        assertEquals(savedChannelDTO, result);
        verify(channelRepository).save(channel);
    }

    @Test
    void testCreateChannelFromDTO_UserNotFound() {
        Long ownerId = 1L;
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setOwnerId(ownerId);

        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> channelService.createChannelFromDTO(channelDTO));
    }

    @Test
    void testGetChannelById_Success() {
        Long channelId = 1L;
        Channel channel = new Channel();
        ChannelDTO channelDTO = new ChannelDTO();

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        when(channelTransformer.convertToDTO(channel)).thenReturn(channelDTO);

        ChannelDTO result = channelService.getChannelById(channelId);

        assertNotNull(result);
        assertEquals(channelDTO, result);
    }

    @Test
    void testGetChannelById_ChannelNotFound() {
        Long channelId = 1L;
        when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.getChannelById(channelId));
    }

    @Test
    void testDeleteChannel_Success() {
        Long channelId = 1L;
        Channel channel = new Channel();

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));

        channelService.deleteChannel(channelId);

        verify(channelRepository).delete(channel);
    }

    @Test
    void testDeleteChannel_ChannelNotFound() {
        Long channelId = 1L;
        when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.deleteChannel(channelId));
    }

    @Test
    void testUpdateChannel_Success() {
        Long channelId = 1L;
        Long ownerId = 2L;
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setOwnerId(ownerId);

        User owner = new User();
        owner.setId(ownerId);

        Channel channel = new Channel();
        Channel updatedChannel = new Channel();
        ChannelDTO updatedChannelDTO = new ChannelDTO();

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(channelRepository.save(channel)).thenReturn(updatedChannel);
        when(channelTransformer.convertToDTO(updatedChannel)).thenReturn(updatedChannelDTO);

        ChannelDTO result = channelService.updateChannel(channelId, channelDTO);

        assertNotNull(result);
        assertEquals(updatedChannelDTO, result);
    }

    @Test
    void testUpdateChannel_ChannelNotFound() {
        Long channelId = 1L;
        ChannelDTO channelDTO = new ChannelDTO();

        when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.updateChannel(channelId, channelDTO));
    }

    @Test
    void testUpdateChannel_UserNotFound() {
        Long channelId = 1L;
        Long ownerId = 2L;
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setOwnerId(ownerId);

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(new Channel()));
        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> channelService.updateChannel(channelId, channelDTO));
    }

    @Test
    void testGetOwnerIdByChannelId_Success() {
        Long channelId = 1L;
        Long ownerId = 2L;

        Channel channel = new Channel();
        User owner = new User();
        owner.setId(ownerId);
        channel.setOwner(owner);

        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));

        Long result = channelService.getOwnerIdByChannelId(channelId);

        assertNotNull(result);
        assertEquals(ownerId, result);
    }

    @Test
    void testGetOwnerIdByChannelId_ChannelNotFound() {
        Long channelId = 1L;
        when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.getOwnerIdByChannelId(channelId));
    }
}
