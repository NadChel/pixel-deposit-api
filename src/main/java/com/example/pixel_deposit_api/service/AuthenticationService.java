package com.example.pixel_deposit_api.service;

import java.util.Optional;

public interface AuthenticationService {

    Optional<Long> getCurrentUserId();
}
