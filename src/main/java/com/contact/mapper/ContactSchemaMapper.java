package com.contact.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.contact.dtos.UserDto;
import com.contact.entities.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactSchemaMapper {

	@Mapping(target = "createdAt", expression = "java(getCurrentTime())")
	@Mapping(target = "password", ignore = true)
	User toUser(UserDto userDto);
	
	default Timestamp getCurrentTime() {
		return Timestamp.valueOf(
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
	
	default String convertTimestampToString(Timestamp timestamp) {
		if(timestamp!=null) {
			String time = timestamp.toString();
			return time.substring(0, time.indexOf("."));
		}
		return null;
	}
}
