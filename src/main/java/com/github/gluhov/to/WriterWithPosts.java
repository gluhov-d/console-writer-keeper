package com.github.gluhov.to;

import com.github.gluhov.model.Status;

import java.util.List;
import java.util.Optional;

public class WriterWithPosts {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Status status;
    private final List<Optional<PostWithLabels>> postsWithLabels;

    public WriterWithPosts(Long id, String firstName, String lastName, Status status, List<Optional<PostWithLabels>> postsWithLabels) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.postsWithLabels = postsWithLabels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Id: " + id + "; First name: " + firstName + "; Last name: " + lastName + "; Status: " + status + "; posts with labels: [ ");
        if (postsWithLabels.size() > 0) {
            for (int i = 0; i < postsWithLabels.size(); i++) {
                sb.append("[ ");
                sb.append(postsWithLabels.get(i).toString());
                if (i != postsWithLabels.size()-1) {
                    sb.append(" ] , ");
                }
            }
            sb.append(" ]");
        }
        sb.append(" ]");
        return sb.toString();
    }
}
