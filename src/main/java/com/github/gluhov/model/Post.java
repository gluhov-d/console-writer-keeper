package com.github.gluhov.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {
    private String title;
    private String content;
    private List<Label> labels;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + "; title: " + title + "; content: " + content + "; status: " + status + "; labels id: [ ");
        if (labels != null) {
            for (int i = 0; i < labels.size(); i++) {
                sb.append("[ ");
                sb.append(labels.get(i));
                if (i != labels.size() -1) {
                    sb.append(" ] , ");
                }
            }
            sb.append(" ]");
        }
        sb.append(" ]");
        return sb.toString();
    }
}
