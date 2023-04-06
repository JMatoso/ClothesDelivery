<footer class="footer bg-dark">
    <div class="container my-auto">
        <div class="row">
            <div class="col-md-8">
                <div class="row row-cols-2">
                    <div class="col-md-3 mb-3">
                        <img src="~/images/logo2.png" alt="Stu2Go Logo" style="width: 75px !important;" />
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6 class="text-white">Info</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a href="mailto:geral@stu2go.com">Email</a>
                            </li>
                            <li class="nav-item">
                                <a href="tel:+244966555777">(244) 966 555 777</a>
                            </li>
                            <li class="nav-item">
                                <span class="text-white text-opacity-75">Luanda, Angola</span>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6 class="text-white">Register</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a asp-page="/account/login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a asp-page="/account/signup">Sign Up</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6 class="text-white">Site</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a asp-page="/about">About</a>
                            </li>
                            <li class="nav-item">
                                <a asp-page="/courses/index">Courses</a>
                            </li>
                            <li class="nav-item">
                                <a asp-page="/contact">Contact</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <h6 class="py-2 text-white text-opacity-75">Subscribe to courses and opening updates.</h6>
                <form class="newsletter" method="get">
                    <input type="email" name="email" required placeholder="Enter your email" />
                    <div class="newsletter-btn">
                        <button type="submit">Subscribe</button>
                    </div>
                </form>

                <div class="contact-social py-2">
                    <span>
                        <a href="https://www.facebook.com/stu2go">
                            <i class="fa-brands fa-facebook"></i>
                        </a>
                    </span>
                    <span>
                        <a href="https://github.com/jmatoso/stu2go">
                            <i class="fa-brands fa-github"></i>
                        </a>
                    </span>
                    <span>
                        <a href="https://wa.me/244966555777">
                            <i class="fa-brands fa-whatsapp"></i>
                        </a>
                    </span>
                    <span>
                        <a href="https://www.instagram.com/stu2go">
                            <i class="fa-brands fa-instagram"></i>
                        </a>
                    </span>
                    <span>
                        <a href="https://www.linkedin.com/in/stu2go">
                            <i class="fa-brands fa-linkedin"></i>
                        </a>
                    </span>
                </div>
            </div>
        </div>
        <div class="copyright my-auto">
            <small class="py-2 fw-normal">
                <span class ="text-white text-opacity-75">Copyright &copy;2023 Stu2Go -</span>
                <span><a asp-page="terms">Terms & Policies</a></span>
            </small>
        </div>
    </div>
</footer>

<script src="assets/libs/jquery/dist/jquery.min.js"></script>
<script src="assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/site.js" asp-append-version="true"></script>

</body>
</html>