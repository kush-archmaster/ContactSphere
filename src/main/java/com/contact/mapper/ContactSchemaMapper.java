package com.contact.mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.contact.dtos.ContactDto;
import com.contact.dtos.UserDto;
import com.contact.entities.Contact;
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
	
	default Date convertStringToDate(String date) {
		if(date!=null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				java.util.Date utilDate = dateFormat.parse(date);
				Date sqlDate = new Date(utilDate.getTime());
				return sqlDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Mapping(target = "password", ignore = true)
	UserDto toUserDto(User user);

	@Mapping(target = "dob", expression = "java(convertStringToDate(contactDto.getDob()))")
	Contact toContact(ContactDto contactDto);
}
