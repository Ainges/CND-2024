package adapter.jpa.repositories;

public class JpaCartRepositoryException extends RuntimeException {
    public JpaCartRepositoryException(String message) {
        super(message);
    }
}
