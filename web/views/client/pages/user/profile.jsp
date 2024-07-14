<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/views/client/includes/header.jsp"/>

<!-- Page Header Start -->
<div class="container-fluid page-header py-5 bg-dark text-white text-center">
    <h1 class="display-4">Profile</h1>
    <ol class="breadcrumb justify-content-center mb-0">
        <li class="breadcrumb-item"><a href="Home" class="text-white">Home</a></li>
        <li class="breadcrumb-item active text-white">Profile</li>
    </ol>
</div>
<!-- Page Header End -->

<!-- Profile Start -->
<div class="container-fluid py-5 d-flex justify-content-center bg-light">
    <div class="container py-5 bg-white shadow rounded">
        <h1 class="mb-4 text-center">Your Profile</h1>
        <div class="row g-4 justify-content-center">
            <!-- Profile Picture Start -->
            <div class="col-lg-3 d-flex align-items-stretch">
                <div class="card mb-4 w-100">
                    <div class="card-body text-center">
                        <img src="${pageContext.request.contextPath}/views/client/asset/img/avatar.jpg" class="img-fluid rounded-circle mb-3" alt="Profile Picture" width="150">
                        <h5 class="card-title">${user.firstName}</h5>
                        <button class="btn btn-secondary w-100" data-bs-toggle="modal" data-bs-target="#changePictureModal">Thêm ảnh</button>
                    </div>
                </div>
            </div>
            <!-- Profile Picture End -->

            <!-- Profile Details and Overview Start -->
            <div class="col-lg-3 d-flex align-items-stretch">
                <div class="card mb-4 w-100">
                    <div class="card-body text-center">
                        <h4 class="card-title">Profile Details</h4>
                        <div class="profile-detail mb-4">
                            <p>
                                <i class="fas fa-envelope me-2"></i>
                                <strong>Email: ${user.email}</strong>
                            </p>
                            <p>
                                <i class="fas fa-lock me-2"></i>
                                <strong>Password: ********* </strong>
                            </p>
                            <p>
                                <i class="fas fa-user me-2"></i>
                                <strong>Name:</strong> ${user.firstName} ${user.lastName}
                            </p>
                            <p>
                                <i class="fas fa-map-marker-alt me-2"></i>
                                <strong>Address:</strong> ${user.address}
                            </p>
                            <p>
                                <i class="fas fa-user-tag me-2"></i>
                                <strong>Role:</strong> ${user.role}
                            </p>
                        </div>
                        </ul>
                        <button class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#editProfileModal">Edit Profile Detail</button>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 d-flex align-items-stretch">
                <div class="card mb-4 w-100">
                    <div class="card-body text-center">
                        <h4 class="card-title">Profile Overview</h4>
                        <div class="profile-detail">
                            <p><strong>Name:</strong> John Doe</p>
                            <p><strong>Address:</strong> Danang</p>
                            <p><strong>Role:</strong> Customer</p>
                            <!-- Additional details -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- Profile Details and Overview End -->
        </div>
    </div>
</div>
<!-- Profile End -->

<!-- Edit Profile Modal -->
<div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editProfileModalLabel">Edit Profile Detail</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="Profile" method="post">
                    <!-- Form fields for editing profile details -->
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" value="${user.password}" required>
                    </div>
                    <!-- Additional fields -->
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Change Picture Modal -->
<div class="modal fade" id="changePictureModal" tabindex="-1" aria-labelledby="changePictureModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePictureModalLabel">Change Profile Picture</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="Profile?action=changePicture" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="profilePicture" class="form-label">Choose New Picture</label>
                        <input type="file" class="form-control" id="profilePicture" name="profilePicture" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Upload Picture</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/client/includes/footer.jsp" />
