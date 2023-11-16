package com.github.gluhov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
    private String title;
    private String content;
    private transient List<Label> labels;
    private List<Long> writersId;

    public Post(Long id, Status status, String title, String content, List<Label> labels, List<Long> writersId) {
        super(id, status);
        this.title = title;
        this.content = content;
        this.labels = labels;
        this.writersId = writersId;
    }

    public Post(Post p) {
        this(p.id, p.status, p.title, p.content, p.labels, p.writersId);
    }

    public Post(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + "; title: " + title + "; content: " + content + "; status: " + status + "; labels id: [ ");
        if (labels != null) {
            for (int i = 0; i < labels.size(); i++) {
                sb.append(labels.get(i));
                if (i != labels.size() -1) {
                    sb.append(" , ");
                }
            }
        }
        sb.append(" ]; writers id: [");
        if (writersId != null) {
            for (int i = 0; i < writersId.size(); i++) {
                sb.append(writersId.get(i));
                if (i != writersId.size()-1) {
                    sb.append(" , ");
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
