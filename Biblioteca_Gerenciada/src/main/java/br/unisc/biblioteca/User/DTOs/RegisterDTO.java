package br.unisc.biblioteca.User.DTOs;

import br.unisc.biblioteca.User.Roles.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
