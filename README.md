# Backend Developer Intern - Task Assignment

### **ReviewController:**

This class is a Spring MVC controller responsible for handling HTTP requests related to user reviews. It interacts with the **`ReviewService`** to perform operations such as adding a review or viewing reviews.

- **`addReview`**: POST mapping ("/review/add-review")
    - Endpoint to add a new review.
    - Accepts a JSON payload (**`ReviewRequest`**) representing the review details.
    - Calls **`reviewService.addReview(reviewRequest)`** to process the request and returns a **`ResponseModel`**.
- **`viewReview`**: GET mapping ("/review/view-review")
    - Endpoint to view reviews for a given session.
    - Accepts a query parameter **`sessionId`**.
    - Calls **`reviewService.viewReview(sessionId)`** to fetch and return reviews for the specified session.

### **PreSessionController:**

This controller manages operations related to pre-session activities, such as document creation, retrieval, and upload. It interacts with the **`PreSessionService`**.

- **`createDocument`**: POST mapping ("/preSession/create-documents")
    - Endpoint for creating pre-session documents.
    - Accepts a JSON payload (**`CreatePreSessionRequest`**) containing document details.
    - Calls **`preSessionService.createDocument(createPreSessionRequest)`** and returns a **`ResponseModel`**.
- **`getDocumentForClient`**: GET mapping ("/preSession/get-documents-to-upload")
    - Endpoint to retrieve documents for a client.
    - Accepts a query parameter **`sessionId`**.
    - Calls **`preSessionService.getDocumentForClient(sessionId)`** to fetch and return documents.
- **`uploadDocument`**: POST mapping ("/preSession/upload-documents")
    - Endpoint to upload documents for a session.
    - Accepts **`sessionId`** as a query parameter and a list of files as form data.
    - Calls **`preSessionService.uploadDocument(sessionId, files)`** to upload documents to an S3 bucket.
- **`getDocumentForConsultant`**: GET mapping ("/preSession/get-documents")
    - Endpoint to retrieve documents for a consultant.
    - Accepts a query parameter **`sessionId`**.
    - Calls **`preSessionService.getDocumentForConsultant(sessionId)`** to fetch and return documents.

### **SessionController:**

This controller handles session-related operations, such as booking sessions and retrieving session history. It communicates with the **`SessionService`**.

- **`bookSession`**: POST mapping ("/session/book-session")
    - Endpoint for booking a new session.
    - Accepts a JSON payload (**`SessionRequest`**) with session details.
    - Calls **`sessionService.bookSession(sessionRequest)`** to process the booking and returns a **`ResponseModel`**.
- **`getSessionHistory`**: GET mapping ("/session/get-session-history")
    - Endpoint to retrieve session history for a client.
    - Accepts a query parameter **`clientId`**.
    - Calls **`sessionService.getSessionHistory(clientId)`** to fetch and return session history.
- **`getUpcomingSession`**: GET mapping ("/session/get-upcoming-session")
    - Endpoint to retrieve upcoming sessions for a consultant.
    - Accepts a query parameter **`consultantId`**.
    - Calls **`sessionService.getUpcomingSession(consultantId)`** to fetch and return upcoming sessions.

### **Entity Classes:**

These classes represent the data model of the application and are mapped to corresponding database tables using JPA annotations. They include:

- **PreSession:** Represents pre-session documents with details like name, description, and whether they are uploaded. Linked to sessions.
- **Review:** Represents user reviews for sessions.
- **Session:** Represents session details, including session date, start time, and end time.