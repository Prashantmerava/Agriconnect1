package com.prashant.agriconnect.dto;

import java.time.LocalDateTime;

public record ApiError(String msg, Object error, LocalDateTime timestamp) {}
