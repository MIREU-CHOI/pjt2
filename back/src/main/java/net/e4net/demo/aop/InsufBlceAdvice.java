package net.e4net.demo.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.exception.InsufBlce;
import net.nurigo.sdk.message.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class InsufBlceAdvice {

	@ExceptionHandler(InsufBlce.class)
	@ResponseStatus()
	public ErrorResponse InsufBlce(RuntimeException e) {
		log.error("balance is insufficient", e);
		// ErrorResponse(errorCode, errorMsg)
		return new ErrorResponse("errorInsuf", "머니 잔액 부족");
	}
}
