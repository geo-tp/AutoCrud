package com.autocrud.main.transformers;

import com.autocrud.main.dtos.ChannelDTO;
import com.autocrud.main.dtos.FieldDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.User;
import com.autocrud.main.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChannelTransformerTest {

    @InjectMocks
    private ChannelTransformer channelTransformer;

    @Mock
    private FieldTransformer fieldTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToDTO() {
        User owner = new User();
        owner.setId(1L);

        Channel channel = new Channel();
        channel.setId(10L);
        channel.setChannelName("Test Channel");
        channel.setOwner(owner);

        Field field = new Field();
        field.setId(100L);
        List<Field> fields = Arrays.asList(field);
        channel.setFields(fields);

        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setId(100L);
        when(fieldTransformer.convertToDTOs(fields)).thenReturn(Arrays.asList(fieldDTO));

        ChannelDTO channelDTO = channelTransformer.convertToDTO(channel);

        assertEquals(channel.getId(), channelDTO.getId());
        assertEquals(channel.getChannelName(), channelDTO.getChannelName());
        assertEquals(channel.getOwner().getId(), channelDTO.getOwnerId());
        assertEquals(1, channelDTO.getFields().size());
        assertEquals(fieldDTO.getId(), channelDTO.getFields().get(0).getId());
    }

    @Test
    void testConvertToEntity() {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setChannelName("Test Channel");

        User owner = new User();
        owner.setId(1L);

        Channel channel = channelTransformer.convertToEntity(channelDTO, owner);

        assertEquals(channelDTO.getChannelName(), channel.getChannelName());
        assertEquals(owner.getId(), channel.getOwner().getId());
    }
}
