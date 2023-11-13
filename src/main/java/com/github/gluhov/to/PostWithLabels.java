package com.github.gluhov.to;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Status;

import java.util.List;

public class PostWithLabels {
    private final Long id;
    private final String title;
    private final String content;
    private final Status status;
    private final List<Label> labels;

    public PostWithLabels(Long id, String title, String content, Status status, List<Label> labels) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.labels = labels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + "; title: " + title + "; content: " + content + "; status: " + status + "; labels: [ ");
        if (labels.size() > 0) {
            for (int i = 0; i < labels.size(); i++) {
                sb.append("[ ");
                sb.append(labels.get(i).toString());

                if (i != labels.size()-1) {
                    sb.append( " ], ");
                }
            }
            sb.append(" ]");
        }
        sb.append("  ]");
        return sb.toString();
    }
}
