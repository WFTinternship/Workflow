<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Forum :: Home Page</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <!-- Custom -->
    <link href="css/custom.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">

    <!-- CSS STYLE-->
    <link rel="stylesheet" type="text/css" href="style.css" media="screen"/>

    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
    <link rel="stylesheet" type="text/css" href="css/settings.css" media="screen"/>

</head>
<body>

<div class="container-fluid">

    <!-- Slider -->
    <div class="tp-banner-container">
        <div class="tp-banner">
            <ul>
                <!-- SLIDE  -->
                <li data-transition="fade" data-slotamount="7" data-masterspeed="1500">
                    <!-- MAIN IMAGE -->
                    <img src="https://www.workfront.com/wp-content/themes/dragons/images/logo-workfront.png"
                         alt="slidebg1" data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat">
                    <!-- LAYERS -->
                </li>
            </ul>
        </div>
    </div>
    <!-- //Slider -->

    <div class="headernav">
        <div class="container">
            <div class="row">


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
                        <form action="03_new_topic.html" method="post" class="form">
                            <button class="btn btn-primary">Start New Topic</button>
                        </form>
                    </div>
                    <div class="env pull-left"><i class="fa fa-envelope"></i></div>


                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>


    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-xs-12 col-md-8">
                    <div class="pull-left"><a href="#" class="prevnext"><i class="fa fa-angle-left"></i></a></div>
                    <div class="pull-left">
                        <ul class="paginationforum">
                            <li class="hidden-xs "><a href="#" class="active">1</a></li>
                            <li class="hidden-xs"><a href="#">2</a></li>
                            <li class="hidden-xs"><a href="#">3</a></li>
                            <li class="hidden-xs"><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">6</a></li>
                            <li><a href="#" class="">7</a></li>
                            <li><a href="#">8</a></li>
                            <li class="hidden-xs"><a href="#">9</a></li>
                            <li class="hidden-xs"><a href="#">10</a></li>
                            <li class="hidden-xs hidden-md"><a href="#">11</a></li>
                            <li class="hidden-xs hidden-md"><a href="#">12</a></li>
                            <li class="hidden-xs hidden-sm hidden-md"><a href="#">13</a></li>
                            <li><a href="#">1586</a></li>
                        </ul>
                    </div>
                    <div class="pull-left"><a href="#" class="prevnext last"><i class="fa fa-angle-right"></i></a></div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">
                    <!-- POST -->
                    <c:forEach var="post" items="${allPosts}" >
                    <div class="post">
                        <div class="wrap-ut pull-left">
                            <div class="userinfo pull-left">
                                <div class="avatar">
                                    <div class="status green">&nbsp;</div>
                                </div>

                            </div>
                            <div class="posttext pull-left">
                                <h2><a href="02_topic.html"> ${post.title}</a></h2>
                                <p> ${post.content}</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="postinfo pull-left">
                            <div class="comments">
                                <div class="commentbg">
                                    560
                                    <div class="mark"></div>
                                </div>

                            </div>
                            <div class="views"><i class="fa fa-eye"></i> 1,568</div>
                            <div class="time"><i class="fa fa-clock-o"></i> 24 min</div>
                        </div>
                        <div class="clearfix"></div>
                    </div><!-- POST -->
                    </c:forEach>

                </div>
                <div class="col-lg-4 col-md-4">

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>Application Areas</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <ul class="cats">
                                <c:forEach var="appArea" items="${appAreas}" >
                                <li><a href="#">${appArea.name}<span class="badge pull-right">20</span></a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <!-- -->

                    <!-- -->

                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-xs-12">
                    <div class="pull-left"><a href="#" class="prevnext"><i class="fa fa-angle-left"></i></a></div>
                    <div class="pull-left">
                        <ul class="paginationforum">
                            <li class="hidden-xs"><a href="#" class="active">1</a></li>
                            <li class="hidden-xs"><a href="#">2</a></li>
                            <li class="hidden-xs"><a href="#">3</a></li>
                            <li class="hidden-xs"><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">6</a></li>
                            <li><a href="#">7</a></li>
                            <li><a href="#">8</a></li>
                            <li class="hidden-xs"><a href="#">9</a></li>
                            <li class="hidden-xs"><a href="#">10</a></li>
                            <li class="hidden-xs hidden-md"><a href="#">11</a></li>
                            <li class="hidden-xs hidden-md"><a href="#">12</a></li>
                            <li class="hidden-xs hidden-sm hidden-md"><a href="#">13</a></li>
                            <li><a href="#">1586</a></li>
                        </ul>
                    </div>
                    <div class="pull-left"><a href="#" class="prevnext last"><i class="fa fa-angle-right"></i></a></div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>


    </section>

    <footer>
        <div class="container">
            <div class="row">
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
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js"></script>


<!-- SLIDER REVOLUTION 4.x SCRIPTS  -->
<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.revolution.min.js"></script>

<script src="js/bootstrap.min.js"></script>


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
</html>