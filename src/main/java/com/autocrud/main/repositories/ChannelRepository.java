package com.autocrud.main.repositories;

import com.autocrud.main.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findByOwnerId(Long ownerId);

    boolean existsByChannelName(String channelName);
}
