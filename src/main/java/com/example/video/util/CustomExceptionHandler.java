package com.example.video.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<HTTPStatusResponse> handleMaxSizeException() {

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), "File size is large must be less than 20MB"),
				HttpStatus.PAYLOAD_TOO_LARGE);

	}
}
