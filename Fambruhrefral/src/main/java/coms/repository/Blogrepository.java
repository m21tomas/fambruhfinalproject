package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coms.model.blog.Blog;
import coms.model.dtos.*;

@Repository
public interface Blogrepository extends JpaRepository<Blog, Long> {
	 Blog findByTitle(String title);
}
