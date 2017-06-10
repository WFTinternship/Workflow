<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from forum.azyrusthemes.post.jsphtml by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:20 GMT -->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Forum :: Topic</title>

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
          rel='stylesheet' type='${pageContext.request.contextPath}/text/css'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome-4.0.3/css/font-awesome.min.css">

    <!-- CSS STYLE-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>

    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/rs-plugin/css/settings.css" media="screen"/>

</head>
<body class="topic">

<div class="container-fluid">
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <span id="form-img"><img src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt="" height="60px" width="60px/"></span>
                </div>
                <form action="http://localhost:8080/login" method="post">
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
                    <img src="${pageContext.request.contextPath}/images/slide.jpg" alt="slidebg1" data-bgfit="cover" data-bgposition="left top"
                         data-bgrepeat="no-repeat">
                    <!-- LAYERS -->
                </li>
            </ul>
        </div>
    </div>
    <!-- //Slider -->


    <div class="headernav">
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 col-md-2 logo "><a href="home.jsp"><img src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt="" height=60px width=60px/></a></div>
                <div class="col-lg-3 col-xs-9 col-sm-5 col-md-3 selecttopic">
                    <div class="dropdown">
                        <a data-toggle="dropdown" href="#">Borderlands 2</a> <b class="caret"></b>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Borderlands 1</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-2" href="#">Borderlands 2</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-3" href="#">Borderlands 3</a></li>

                        </ul>
                    </div>
                </div>
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
                <div class="col-lg-4 col-xs-12 col-sm-5 col-md-4 avt">
                    <div class="stnt pull-left">

                            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">Add New Post</button>

                    </div>
                    <div class="env pull-left"><i class="fa fa-envelope"></i></div>

                    <div class="avatar pull-left dropdown">
                        <a data-toggle="dropdown" href="#"><img src="http://localhost:8080/images/avatar.jpg" alt=""/></a> <b
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
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>


    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 breadcrumbf">
                    <a href="#">Borderlands 2</a> <span class="diviver">&gt;</span> <a href="#">General Discussion</a>
                    <span class="diviver">&gt;</span> <a href="#">Topic Details</a>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">

                    <!-- POST -->

                    <div class="post beforepagination">
                        <div class="topwrap">
                            <div class="userinfo pull-left">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/images/avatar.jpg" alt=""/>
                                    <div class="status green">&nbsp;</div>
                                </div>

                                <div class="icons">
                                    <img src="${pageContext.request.contextPath}/images/icon1.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon4.jpg" alt=""/><img
                                        src="${pageContext.request.contextPath}/images/icon5.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon6.jpg" alt=""/>
                                </div>
                            </div>
                            <div class="posttext pull-left">
                                <h2>${post.title}</h2>
                                <p>${post.content}</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="postinfobot">

                            <div class="likeblock pull-left">
                                <a href="#" class="up"><i class="fa fa-thumbs-o-up"></i>25</a>
                                <a href="#" class="down"><i class="fa fa-thumbs-o-down"></i>3</a>
                            </div>

                            <div class="prev pull-left">
                                <a href="#"><i class="fa fa-reply"></i></a>
                            </div>

                            <div class="posted pull-left"><i class="fa fa-clock-o"></i> Posted on : 20 Nov @ 9:30am
                            </div>

                            <div class="next pull-right">
                                <a href="#"><i class="fa fa-share"></i></a>

                                <a href="#"><i class="fa fa-flag"></i></a>
                            </div>

                            <div class="clearfix"></div>
                        </div>
                    </div><!-- POST -->

                    <div class="paginationf">
                        <div class="pull-left"><a href="#" class="prevnext"></a></div>
                        <div class="pull-left">

                        </div>
                        <div class="pull-left"><a href="#" class="prevnext last"></a>
                        </div>
                        <div class="clearfix"></div>
                    </div>

                    <!-- POST -->
                    <c:forEach var="answer" items="${answers}">
                        <div class="post">
                            <div class="topwrap">
                                <div class="userinfo pull-left">
                                    <div class="avatar">
                                        <img src="${pageContext.request.contextPath}/images/avatar2.jpg" alt=""/>
                                        <div class="status red">&nbsp;</div>
                                    </div>

                                    <div class="icons">
                                        <img src="${pageContext.request.contextPath}/images/icon3.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon4.jpg" alt=""/><img
                                            src="${pageContext.request.contextPath}/images/icon5.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon6.jpg" alt=""/>
                                    </div>
                                </div>
                                <div class="posttext pull-left">
                                    <p>${answer.content}</p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="postinfobot">

                                <div class="likeblock pull-left">
                                    <a href="#" class="up"><i class="fa fa-thumbs-o-up"></i>10</a>
                                    <a href="#" class="down"><i class="fa fa-thumbs-o-down"></i>1</a>
                                </div>

                                <div class="prev pull-left">
                                    <a href="#"><i class="fa fa-reply"></i></a>
                                </div>

                                <div class="posted pull-left"><i class="fa fa-clock-o"></i> Posted on : 20 Nov @ 9:45am
                                </div>

                                <div class="next pull-right">
                                    <a href="#"><i class="fa fa-share"></i></a>

                                    <a href="#"><i class="fa fa-flag"></i></a>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- POST -->


                    <!-- POST -->


                    <!-- POST -->
                    <div class="post">
                        <form action="#" class="form" method="post">
                            <div class="topwrap">
                                <div class="userinfo pull-left">
                                    <div class="avatar">
                                        <img src="${pageContext.request.contextPath}/images/avatar4.jpg" alt=""/>
                                        <div class="status red">&nbsp;</div>
                                    </div>

                                    <div class="icons">
                                        <img src="${pageContext.request.contextPath}/images/icon3.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon4.jpg" alt=""/><img
                                            src="${pageContext.request.contextPath}/images/icon5.jpg" alt=""/><img src="${pageContext.request.contextPath}/images/icon6.jpg" alt=""/>
                                    </div>
                                </div>
                                <div class="posttext pull-left">
                                    <div class="textwraper">
                                        <div class="postreply">Post a Reply</div>
                                        <textarea name="reply" id="reply"
                                                  placeholder="Type your message here"></textarea>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="postinfobot">

                                <div class="notechbox pull-left">
                                    <input type="checkbox" name="note" id="note" class="form-control"/>
                                </div>

                                <div class="pull-left">
                                    <label for="note"> Email me when some one post a reply</label>
                                </div>

                                <div class="pull-right postreply">
                                    <div class="pull-left smile"><a href="#"><i class="fa fa-smile-o"></i></a></div>
                                    <div class="pull-left">
                                        <button type="submit" class="btn btn-primary">Post Reply</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>


                                <div class="clearfix"></div>
                            </div>
                        </form>
                    </div><!-- POST -->


                </div>
                <div class="col-lg-4 col-md-4">

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>Application Areas</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <ul class="cats">
                                <c:forEach var="appArea" items="${appAreas}" >
                                <li><a href="#">${appArea.name} <span class="badge pull-right">20</span></a></li>
                                </c:forEach>
                            </ul>
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


        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="pull-left"><a href="#" class="prevnext"></a></div>
                    <div class="pull-left">

                    </div>
                    <div class="pull-left"><a href="#" class="prevnext last"></a></div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>

    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 logo "><a href="#"><img src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt="" height=60px width=60px/></a></div>
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

<!-- END REVOLUTION SLIDER -->
</body>

<!-- Mirrored from forum.azyrusthemes.post.jsphtml by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:21 GMT -->
</html>