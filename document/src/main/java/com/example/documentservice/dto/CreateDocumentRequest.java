package com.example.documentservice.dto;

public  class CreateDocumentRequest {
    private Long courseId;
    private String content;
    private Long userId;
    private String role;

   public CreateDocumentRequest(Long courseId, String content, Long userId, String role) {
       this.courseId = courseId;
       this.content = content;
       this.userId = userId;
       this.role = role;
   }
   public CreateDocumentRequest() {}
   public Long getCourseId() {
       return courseId;
   }
    public String getContent() {
       return content;
    }
    public void setContent(String content) {
       this.content = content;
    }
        public void setCourseId(Long courseId) {
       this.courseId = courseId;
        }
        public Long getUserId() {
        return userId;
        }

        public void setUserId(Long userId) {
       this.userId = userId;
        }
        public String getRole() {
        return role;
        }
        public void setRole(String role) {
       this.role = role;
        }
}
