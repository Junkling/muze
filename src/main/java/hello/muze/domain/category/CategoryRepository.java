package hello.muze.domain.category;

import hello.muze.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CategoryRepository {
    private static Map<Integer, Category> store = new HashMap<>();
    private static int sequence;
    public Category save(Category category) {
        category.setId(++sequence);
        category.setCreated(LocalDateTime.now());
        store.put(category.getId(), category);
        return category;
    }
    public Category findById(Integer id) {
        return store.get(id);
    }

    public Optional<Category> findByName(String categoryName) {
        return findAll().stream().filter(category -> category.getCategoryName().equals(categoryName)).findFirst();
    }

    public List<Category> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}

