package com.yagubogu.auth.support;

import com.yagubogu.auth.dto.AuthResponse;
import com.yagubogu.member.domain.OAuthProvider;

public interface AuthValidator<T extends AuthResponse> {

    boolean supports(OAuthProvider provider);

    void validate(T response);
}
