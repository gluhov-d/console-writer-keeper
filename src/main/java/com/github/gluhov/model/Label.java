package com.github.gluhov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Label extends BaseEntity{
    private String name;
    private List<Long> postsId;

    public Label(Long id, Status status, String name, List<Long> postsId) {
        super(id, status);
        this.name = name;
        this.postsId = postsId;
    }

    public Label(Label l) {
        this(l.id, l.status, l.name, l.postsId);
    }

    public Label(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + "; name: " + name + "; status: " + status + "; posts id: [ ");
        if (postsId != null) {
            for (int i = 0; i < postsId.size(); i++) {
                sb.append(postsId.get(i));
                if (i != postsId.size()-1) {
                    sb.append(" , ");
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
