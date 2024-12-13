package org.authentication.servicespringboot.Auth.DTO;

public class JWTAuthResponseDTO {
    private String token;
    private String tokenType = "Bearer";

    public JWTAuthResponseDTO() {
    }

    public JWTAuthResponseDTO(String token) {
        this.token = token;
    }

    public JWTAuthResponseDTO(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
