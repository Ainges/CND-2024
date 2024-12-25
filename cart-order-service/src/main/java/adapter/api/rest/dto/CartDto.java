package adapter.api.rest.dto;

public class CartDto {

        private long id;
        private long userId;
        private String status;

        public CartDto(long id, long userId, String status) {
            this.id = id;
            this.userId = userId;
            this.status = status;
        }

        public long getId() {
            return id;
        }

        public long getUserId() {
            return userId;
        }

        public String getStatus() {
            return status;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}
