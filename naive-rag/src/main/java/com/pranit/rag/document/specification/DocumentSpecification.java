package com.pranit.rag.document.specification;

import com.pranit.rag.entities.entity.Document;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class DocumentSpecification {

    private DocumentSpecification() {
    }

    public static Specification<Document> searchKeyword(final String keyword) {
        return ((root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return cb.conjunction();
            final String likePattern = "%" + keyword.toLowerCase() + "%";
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("fileName")), likePattern));
            return cb.or(predicates.toArray(new Predicate[0]));
        });
    }
}
