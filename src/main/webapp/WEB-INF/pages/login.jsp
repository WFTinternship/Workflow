<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page import="com.workfront.internship.workflow.web.PageAttributes" %>
<c:set var="message" value='<%=request.getAttribute(PageAttributes.MESSAGE)%>'/>
<c:set var="user" value='<%=request.getAttribute(PageAttributes.USER)%>'/>
<c:set var="avatar" value='<%=request.getAttribute("avatar")%>'/>

<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from forum.azyrusthemes.com/04_new_account.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:13 GMT -->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Forum :: New account</title>

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
    <!-- Slider -->
    <div class="tp-banner-container">
        <div class="tp-banner">
            <ul>
                <!-- SLIDE  -->
                <li data-transition="fade" data-slotamount="7" data-masterspeed="1500">
                    <!-- MAIN IMAGE -->
                    <img src="${pageContext.request.contextPath}/images/slide.jpg" alt="slidebg1" data-bgfit="cover"
                         data-bgposition="left top" data-bgrepeat="no-repeat">
                    <!-- LAYERS -->
                </li>
            </ul>
        </div>
    </div>
    <!-- //Slider -->


    <div class="headernav">
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 col-md-2 logo "><a href="/"><img
                        src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt=""
                        height=60px width=60px/></a></div>
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
                        <form action="#" method="post" class="form">
                            <div class="pull-left txt"><input type="text" class="form-control"
                                                              placeholder="Search Topics"></div>
                            <div class="pull-right">
                                <button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
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
                                src="${pageContext.request.contextPath}/images/avatar.jpg" alt=""/></a> <b
                            class="caret"></b>
                        <div class="status green">&nbsp;</div>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">My Profile</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-2" href="#">Inbox</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-3" href="#">Log Out</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-4" href="login.jsp">Create
                                account</a></li>
                        </ul>
                    </div>

                </c:if>


            </div>
        </div>
    </div>


    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 breadcrumbf">
                    <font color="red">${message}</font>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#signup">Sign up</a></li>
                        <li><a data-toggle="tab" href="#login">Login</a></li>
                    </ul>

                    <div class="tab-content">
                        <div id="signup" class="tab-pane fade in active">

                            <!-- POST -->
                            <div class="post">
                                <form action="/signup" class="form newtopic" method="post" enctype="multipart/form-data">
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
                                                <div class="avatar">
                                                    <img src="${pageContext.request.contextPath}/images/avatar-blank.jpg" id="image" alt="" height="60" width="60px"/>
                                                    <%--<img src="${pageContext.request.contextPath}/images/avatar-blank.jpg"--%>
                                                         <%--alt=""/>--%>
                                                </div>
                                                <%--<div class="imgsize">60 x 60</div>--%>
                                                <%--<div>--%>
                                                    <%--<button class="btn">Add</button>--%>
                                                    <%--&lt;%&ndash;<input class="input_file" name="avatar" id="avatar" type="file">&ndash;%&gt;--%>

                                                <%--</div>--%>
                                                <div class="half-width">
                                                    <input type="file" name="avatar" id="avatar" class="hide"/>
                                                    <label for="avatar" class="btn">Add</label><br/>
                                                </div>
                                            </div>
                                            <div class="posttext pull-left">
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6">
                                                        <input type="text" placeholder="First Name" class="form-control"
                                                               name="firstName"/>
                                                    </div>
                                                    <div class="col-lg-6 col-md-6">
                                                        <input type="text" placeholder="Last Name" class="form-control"
                                                               name="lastName"/>
                                                    </div>
                                                </div>
                                                <div>
                                                    <input type="text" placeholder="Email" class="form-control"
                                                           name="email"/>
                                                </div>
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6">
                                                        <input type="password" placeholder="Password"
                                                               class="form-control" id="sgpass" name="password"/>
                                                    </div>
                                                    <div class="col-lg-6 col-md-6">
                                                        <input type="password" placeholder="Retype Password"
                                                               class="form-control" id="sgpass2" name="confirmPass"/>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div><!-- acc section END -->


                                    <div class="postinfobot">

                                        <div class="notechbox pull-left">
                                            <input type="checkbox" name="note" id="sgnote" class="form-control"/>
                                        </div>

                                        <div class="pull-left lblfch">
                                            <label for="note"> I agree with the Terms and Conditions of this
                                                site</label>
                                        </div>

                                        <div class="pull-right postreply">
                                            <div class="pull-left smile"><a href="#"><i class="fa fa-smile-o"></i></a>
                                            </div>
                                            <div class="pull-left">
                                                <button type="submit" class="btn btn-primary">Sign Up</button>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>


                                        <div class="clearfix"></div>
                                    </div>
                                </form>
                            </div><!-- POST -->


                        </div>
                        <div id="login" class="tab-pane fade">

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
                                                        <input type="text" placeholder="Email" class="form-control"
                                                               name="email"/>

                                                    </div>
                                                    <div class="col-lg-12 col-md-12">
                                                        <input type="password" placeholder="Password"
                                                               class="form-control" id="pass" name="password"/>
                                                    </div>

                                                </div>

                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div><!-- acc section END -->


                                    <div class="postinfobot">

                                        <div class="notechbox pull-left">
                                            <input type="checkbox" name="note" id="note" class="form-control"/>
                                        </div>

                                        <div class="pull-left lblfch">
                                            <label for="note"> I agree with the Terms and Conditions of this
                                                site</label>
                                        </div>

                                        <div class="pull-right postreply">
                                            <div class="pull-left smile"><a href="#"><i class="fa fa-smile-o"></i></a>
                                            </div>
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
                                <c:forEach var="appArea" items="${appAreas}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/appArea/${appArea.id}">${appArea.name}<span
                                            class="badge pull-right"></span></a></li>
                                </c:forEach>
                        </div>
                    </div>

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>Poll of the Week</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <p>Which game you are playing this week?</p>
                            <form action="#" method="post" class="form">
                                <table class="poll">
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color1" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 90%">
                                                    Call of Duty Ghosts
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt1" type="radio" name="opt" value="1">
                                            <label for="opt1"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color2" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 63%">
                                                    Titanfall
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt2" type="radio" name="opt" value="2" checked>
                                            <label for="opt2"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color3" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 75%">
                                                    Battlefield 4
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt3" type="radio" name="opt" value="3">
                                            <label for="opt3"></label>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                            <p class="smal">Voting ends on 19th of October</p>
                        </div>
                    </div>

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>My Active Threads</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">This Dock Turns Your iPhone Into a Bedside Lamp</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Who Wins in the Battle for Power on the Internet?</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Sony QX10: A Funky, Overpriced Lens Camera for Your Smartphone</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">FedEx Simplifies Shipping for Small Businesses</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Loud and Brave: Saudi Women Set to Protest Driving Ban</a>
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
                        src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt=""
                        height=60px width=60px/></a></div>
                <div class="col-lg-8 col-xs-9 col-sm-5 ">Copyrights 2014, websitename.com</div>
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
        </div>
    </footer>
</div>


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


<!-- END REVOLUTION SLIDER -->
</body>

<!-- Mirrored from forum.azyrusthemes.com/04_new_account.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:13 GMT -->
</html>