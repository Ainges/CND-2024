package adapter.jpa.repositories;

public class JpaOrderRepositoryException extends RuntimeException {
    public JpaOrderRepositoryException(String message) {
        super(message);
    }
}
