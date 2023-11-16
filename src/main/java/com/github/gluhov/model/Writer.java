package com.github.gluhov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Writer extends BaseEntity{
    private String firstName;
    private String lastName;
    private transient List<Post> posts;

    public Writer(Long id, Status status, String firstName, String lastName) {
        super(id, status);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(Writer w) {
        this(w.id, w.status, w.firstName, w.lastName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + " ; first name: " + firstName + " ; last name: " + lastName + " ; status: " + status + "; posts id: [ ");
        if (posts != null) {
            for (int i = 0; i < posts.size(); i++) {
                sb.append(posts.get(i));
                if (i != posts.size()-1) {
                    sb.append(" , ");
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
