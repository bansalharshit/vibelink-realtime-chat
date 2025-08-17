package com.harshit.vibelink.config;

import com.harshit.vibelink.entity.Room;
import com.harshit.vibelink.entity.User;
import com.harshit.vibelink.repository.RoomRepository;
import com.harshit.vibelink.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoomRepository roomRepo, UserRepository userRepo) {
        return args -> {
            if (roomRepo.count() == 0) {
                roomRepo.save(new Room("general"));
                roomRepo.save(new Room("sports"));
                roomRepo.save(new Room("tech"));
            }
            if (userRepo.count() == 0) {
                userRepo.save(new User("Admin"));
            }
        };
    }
}

