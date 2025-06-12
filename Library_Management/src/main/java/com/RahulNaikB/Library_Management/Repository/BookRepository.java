package com.RahulNaikB.Library_Management.Repository;

import com.RahulNaikB.Library_Management.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long >
{

}
