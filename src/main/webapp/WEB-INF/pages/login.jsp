<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page import="com.workfront.internship.workflow.controller.PageAttributes" %>
<c:set var="mostDiscussedPosts" value='<%=request.getAttribute(PageAttributes.MOST_DISCUSSED_POSTS)%>'/>
<c:set var="topPosts" value='<%=request.getAttribute(PageAttributes.TOP_POSTS)%>'/>

<c:set var="numberOfAnswersForMDP" value='<%=request.getAttribute(PageAttributes.NUM_OF_ANSWERS_FOR_MDP)%>'/>
<c:set var="difOfLikesDislikes" value='<%=request.getAttribute(PageAttributes.DIF_OF_LIKES_DISLIKES)%>'/>

<c:set var="message" value='<%=request.getAttribute(PageAttributes.MESSAGE)%>'/>
<c:set var="user" value='<%=request.getAttribute(PageAttributes.USER)%>'/>
<c:set var="postsBySameAppAreaID" value='<%=request.getAttribute(PageAttributes.POSTS_OF_APPAREA)%>'/>
<c:set var="avatar" value='<%=request.getAttribute(PageAttributes.AVATAR)%>'/>
<c:set var="email" value='<%=request.getAttribute(PageAttributes.EMAIL)%>'/>
<c:set var="loginRequest" value='<%=request.getAttribute(PageAttributes.LOGIN_REQUEST)%>'/>

<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from forum.azyrusthemes.com/04_new_account.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:13 GMT -->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Workflow</title>
    <%----%>
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="32x32"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="96x96"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="16x16"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="manifest" href="/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <%----%>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom -->
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome-4.0.3/css/font-awesome.min.css">

    <!-- CSS STYLE-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>

    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/rs-plugin/css/settings.css"
          media="screen"/>

</head>
<body class="newaccountpage">
<div id="loader" style="display: none;">
    <div class="leftEye"></div>
    <div class="rightEye"></div>
    <div class="mouth"></div>
</div>
<div class="container-fluid">
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <span id="form-img"><img
                            src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt=""
                            height="60px" width="60px/"></span>
                </div>
                <form action="/login/new-post" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="usr">Name:</label>
                            <input type="text" class="form-control" name="email" id="usr">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Password:</label>
                            <input type="password" class="form-control" name="password" id="pwd">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-login">Login</button>
                    </div>
                </form>
            </div>

        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="verify" role="dialog" data-keyboard="false" data-backdrop="static">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" id="close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action="/signup/verify" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="pwd">Verification Code:</label>
                            <input type="password" class="form-control" name="verify" value="">
                            <input type="hidden" id="afterajaxemail" name="emailajax">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-login">Sign Up</button>
                        <button type="submit" class="btn btn-login">Cancel</button>
                    </div>
                </form>
            </div>

        </div>
    </div>

    <%--<!-- Slider -->--%>
    <%--<div class="tp-banner-container">--%>
    <%--<div class="tp-banner">--%>
    <%--<ul>--%>
    <%--<!-- SLIDE  -->--%>
    <%--<li data-transition="fade" data-slotamount="7" data-masterspeed="1500">--%>
    <%--<!-- MAIN IMAGE -->--%>
    <%--<img src="${pageContext.request.contextPath}/images/slide.jpg" alt="slidebg1" data-bgfit="cover"--%>
    <%--data-bgposition="left top" data-bgrepeat="no-repeat">--%>
    <%--<!-- LAYERS -->--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<!-- //Slider -->--%>


    <div class="headernav" id="header">

        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 col-md-2 logo "><a href="/"><img
                        src="/images/logo.png" alt=""
                        height=67px width=67px/></a></div>
                <%--<div class="col-lg-3 col-xs-9 col-sm-5 col-md-3 selecttopic">--%>
                <%--<div class="dropdown">--%>
                <%--<a data-toggle="dropdown" href="#">Borderlands 2</a> <b class="caret"></b>--%>
                <%--<ul class="dropdown-menu" role="menu">--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Borderlands 1</a></li>--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-2" href="#">Borderlands 2</a></li>--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-3" href="#">Borderlands 3</a></li>--%>

                <%--</ul>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="col-lg-4 search hidden-xs hidden-sm col-md-3">
                    <div class="wrap">
                        <form action="/searchPost" method="post" class="form">
                            <div class="pull-left txt"><input type="text" class="form-control" name="postTitle"
                                                              placeholder="Search posts"></div>
                            <div class="pull-right">
                                <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-7 col-xs-12 col-sm-5 col-md-7 avt">
                    <div class="stnt">
                        <c:if test="${user == null}">
                            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">Add New Post
                            </button>
                        </c:if>

                        <c:if test="${user != null}">
                            <a href="/new-post">
                                <button class="btn btn-primary">Add New Post</button>
                            </a>
                        </c:if>

                        <c:if test="${user == null}">
                          <span>
                              <a href="/signup"><button type="submit" class="btn btn-signup">Sign Up</button></a>
                              <a href="/login"><button type="submit" class="btn btn-login">Login</button></a>
                          </span>
                        </c:if>
                    </div>

                    <div class="clearfix"></div>
                </div>

                <c:if test="${user != null}">
                    <div class="avatar pull-left dropdown">
                        <a data-toggle="dropdown" href="#"><img
                                src="/images/avatar.jpg" alt=""/></a> <b
                            class="caret"></b>
                        <div class="status green">&nbsp;</div>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">My Profile</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-3" href="#">Log Out</a></li>

                        </ul>
                    </div>

                </c:if>


            </div>
        </div>
    </div>
    <div class="notify"></div>

    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 breadcrumbf">
                    <c:if test="${message != null}">
                        <div id="alert" class="alert-success alert-info-success"><strong>Info!</strong> ${message}</div>
                    </c:if>
                    <c:if test="${message == 'Sorry, the code is invalid.'}">
                        <div id="alert" class="alert-success alert-info-failure"><strong>Info!</strong> ${message}</div>
                    </c:if>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">
                    <c:if test="${loginRequest}">
                        <ul class="nav nav-tabs">
                            <li><a data-toggle="tab" href="#signup">Sign up</a></li>
                            <li class="active"><a data-toggle="tab" href="#login">Login</a></li>
                        </ul>
                    </c:if>
                    <c:if test="${!loginRequest}">
                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#signup">Sign up</a></li>
                            <li><a data-toggle="tab" href="#login">Login</a></li>
                        </ul>
                    </c:if>

                    <div class="tab-content">
                        <c:if test="${!loginRequest}">
                        <div id="signup" class="tab-pane fade in active">
                            </c:if>
                            <c:if test="${loginRequest}">
                            <div id="signup" class="tab-pane fade">
                                </c:if>

                                <!-- POST -->
                                <div class="post">
                                    <form class="form newtopic" method="post" enctype="multipart/form-data">
                                        <div class="postinfotop">
                                            <h2>Create New Account</h2>
                                        </div>

                                        <!-- acc section -->
                                        <div class="accsection">
                                            <div class="acccap">
                                                <div class="userinfo pull-left">&nbsp;</div>
                                                <div class="posttext pull-left"><h3>Required Fields</h3></div>
                                                <div class="clearfix"></div>
                                            </div>
                                            <div class="topwrap">
                                                <div class="userinfo pull-left">

                                                </div>
                                                <div class="posttext pull-left">
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6">
                                                            <input type="text" placeholder="First Name"
                                                                   class="form-control"
                                                                   name="firstName" requir
                                                                   ed/>
                                                        </div>
                                                        <div class="col-lg-6 col-md-6">
                                                            <input type="text" placeholder="Last Name"
                                                                   class="form-control"
                                                                   name="lastName" required/>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <input type="text" placeholder="Email" class="form-control"
                                                               name="email" id="email" required/>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6">
                                                            <input type="password" placeholder="Password"
                                                                   class="form-control" id="sgpass" name="password"
                                                                   required/>
                                                        </div>
                                                        <div class="col-lg-6 col-md-6">
                                                            <input type="password" placeholder="Retype Password"
                                                                   class="form-control" id="sgpass2" name="confirmPass"
                                                                   required/>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div><!-- acc section END -->


                                        <div class="postinfobot">


                                            <div class="pull-right postreply">
                                                <div class="pull-left">
                                                    <input type="button" class="btn btn-primary" id="vcodeajax"
                                                           value="Sign Up">
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>


                                            <div class="clearfix"></div>
                                        </div>
                                    </form>
                                </div><!-- POST -->
                            </div>

                            <c:if test="${loginRequest}">
                            <div id="login" class="tab-pane fade in active">
                                </c:if>

                                <c:if test="${!loginRequest}">
                                <div id="login" class="tab-pane fade">
                                    </c:if>

                                    <!-- POST -->
                                    <div class="post">
                                        <form action="/login" class="form newtopic" method="post">
                                            <div class="postinfotop">
                                                <h2>Log in to account</h2>
                                            </div>

                                            <!-- acc section -->
                                            <div class="accsection">

                                                <div class="topwrap">
                                                    <div class="posttext container">

                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12">
                                                                <input type="text" placeholder="Email"
                                                                       class="form-control"
                                                                       name="email" required/>

                                                            </div>
                                                            <div class="col-lg-12 col-md-12">
                                                                <input type="password" placeholder="Password"
                                                                       class="form-control" id="pass" name="password"
                                                                       required/>
                                                            </div>

                                                        </div>

                                                    </div>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </div><!-- acc section END -->


                                            <div class="postinfobot">

                                                <div class="pull-right postreply">
                                                    <div class="pull-left">
                                                        <button type="submit" class="btn btn-primary">Login</button>
                                                    </div>
                                                    <div class="clearfix"></div>
                                                </div>


                                                <div class="clearfix"></div>
                                            </div>
                                        </form>
                                    </div><!-- POST -->

                                </div>

                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4">

                            <!-- -->
                            <div class="sidebarblock">
                                <h3>Application Area</h3>
                                <div class="divline"></div>
                                <div class="blocktxt">
                                    <ul class="cats">
                                        <c:forEach var="appArea" items="${appAreas}" varStatus="status">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/appArea/${appArea.id}">${appArea.name}</a>
                                            <span class="badge pull-right">${postsBySameAppAreaID[status.index]}</span>
                                        </li>
                                        </c:forEach>
                                </div>
                            </div>

                            <!-- -->
                            <div class="sidebarblock">
                                <a href="/mostDiscussedPosts"><h3>Most discussed posts</h3></a>
                                <c:forEach var="post" items="${mostDiscussedPosts}" varStatus="status">
                                    <div class="divline"></div>
                                    <div class="blocktxt">
                                        <a href="/post/${post.id}">${post.title}</a>
                                        <span class="badge pull-right">${numberOfAnswersForMDP[status.index]}</span>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- -->
                            <div class="sidebarblock">
                                <a href="/topPosts"><h3>Top Posts</h3></a>

                                <c:forEach var="post" items="${topPosts}" varStatus="status">
                                    <div class="divline"></div>
                                    <div class="blocktxt">
                                        <a href="/post/${post.id}">${post.title}</a>
                                        <span class="badge pull-right">${difOfLikesDislikes[status.index]}</span>
                                    </div>
                                </c:forEach>


                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 logo "><a href="/"><img
                        src="/images/logo.png" alt=""
                        height=67px width=67px/></a></div>
                <div class="col-lg-8 col-xs-9 col-sm-5 ">Workflow 2017</div>
                <div class="col-lg-3 col-xs-12 col-sm-5 sociconcent">
                    <ul class="socialicons">
                        <li><a href="#"><i class="fa fa-facebook-square"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                        <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                        <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                        <li><a href="#"><i class="fa fa-cloud"></i></a></li>
                        <li><a href="#"><i class="fa fa-rss"></i></a></li>
                    </ul>
                </div>
            </div>
            id
        </div>
    </footer>


    <!-- get jQuery from the google apis -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- SLIDER REVOLUTION 4.x SCRIPTS  -->
    <script type="text/javascript" src="rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
    <script type="text/javascript" src="rs-plugin/js/jquery.themepunch.revolution.min.js"></script>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <!-- LOOK THE DOCUMENTATION FOR MORE INFORMATIONS -->
    <script type="text/javascript">
        var revapi;
        jQuery(document).ready(function () {
            "use strict";
            revapi = jQuery('.tp-banner').revolution(
                {
                    delay: 15000,
                    startwidth: 1200,
                    startheight: 278,
                    hideThumbs: 10,
                    fullWidth: "on"
                });
        });	//ready
    </script>

    <script>document.getElementById("avatar").onchange = function () {
        var reader = new FileReader();
        reader.onload = function (e) {
            // get loaded data and render thumbnail.
            document.getElementById("image").src = e.target.result;
        };
        // read the image file as a data URL.
        reader.readAsDataURL(this.files[0]);
    };
    </script>


    <script>
        jQuery("#vcodeajax").click(function () {
            var firstName = jQuery("input[name=firstName]").val();
            if(jQuery("input[name=firstName]").val() || jQuery("input[name=lastName]").val() || jQuery("#email").val() || jQuery("#sgpass").val() || jQuery("#sgpass2").val()){
                jQuery("#loader").show();
                $.ajax({
                    url: '/signup',
                    type: 'POST',
                    data: {
                        'firstName': jQuery("input[name=firstName]").val(),
                        'lastName': jQuery("input[name=lastName]").val(),
                        'email': jQuery("#email").val(),
                        'password': jQuery("#sgpass").val(),
                        'confirmPass': jQuery("#sgpass2").val()
                    }, statusCode: {
                        409: function (response) {
                            $('.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong>This email is already used!</div>').fadeIn("slow");
                            jQuery("#loader").hide();
                        },
                        403: function (response) {
                            $('.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong>Email is not valid, could not send verification code. Please try again.</div>').fadeIn("slow");
                            jQuery("#loader").hide();
                        },
                        500: function (response) {
                            $('.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong>We were not able to send the email. Please try again.</div>').fadeIn("slow");
                            jQuery("#loader").hide();
                        },
                        400: function (response) {
                            $('.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong>Password does not match</div>').fadeIn("slow");
                            jQuery("#loader").hide();
                        }
                    }, success: function () {
                        jQuery("#afterajaxemail").val(jQuery("#email").val());
                        $('#verify').modal('toggle');
                        jQuery("#loader").hide();

                    },
                    error: function (errorThrow) {
                        console.log('error')
                    }
                });
            }else{
                console.log("blank");
                $('.notify').html('<div id="alert" class="alert alert-info"><strong>Info!</strong>Please fill required fields</div>').fadeIn("slow");


            }


        });
    </script>

    <!-- Custom -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>

    <!-- END REVOLUTION SLIDER -->
</body>

<!-- Mirrored from forum.azyrusthemes.com/04_new_account.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:13 GMT -->
</html>