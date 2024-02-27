package br.unisc.biblioteca.User.DTOs;

import br.unisc.biblioteca.User.Roles.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
