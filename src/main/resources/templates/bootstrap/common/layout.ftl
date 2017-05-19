<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
${weibometa!}
  <title>${page_title!siteTitle}</title>
  <link rel="icon" href="//zgqq.cn/favicon.ico">

  <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css"/>
<#--<link rel="stylesheet/less" href="/static/bootstrap/css/less/bootstrap.less">-->
<#--<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>-->
  <link rel="stylesheet" href="/static/bootstrap/css/github.css">
  <link rel="stylesheet" href="/static/bootstrap/css/awebbs.css">
  <script src="/static/bootstrap/js/jquery.min.js"></script>
  <script src="/static/bootstrap/js/bootstrap.min.js"></script>
  <script src="/static/bootstrap/libs/layer/layer.js"></script>
</head>
<body>
<div class="wrapper">
  <#include "./header.ftl">
  <@header page_tab=page_tab/>
  <div class="container">
    <#nested />
  </div>
</div>
  <#include "./footer.ftl">
  <@footer/>
<script src="/static/bootstrap/js/awebbs.js"></script>
</body>
</html>
</#macro>