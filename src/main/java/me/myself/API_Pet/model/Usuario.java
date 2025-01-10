package me.myself.API_Pet.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String user;

    @Column(nullable = false)
    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pessoa pessoa;


    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Usuario setUser(String user) {
        this.user = user;
        return this; // Retorna a instância atual
    }

    public Usuario setSenha(String senha) {
        this.senha = senha;
        return this; // Retorna a instância atual
    }

    // Implementação dos métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui você pode retornar as permissões do usuário (roles).
        // Exemplo:
        // return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(); // Por enquanto, sem roles definidas.
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String getPassword() {
        return senha; // Campo senha usado como password.
    }

    @Override
    public String getUsername() {
        return user; // Campo user usado como username.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Define se a conta está expirada.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Define se a conta está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Define se as credenciais estão expiradas.
    }

    @Override
    public boolean isEnabled() {
        return true; // Define se o usuário está ativo.
    }
}
