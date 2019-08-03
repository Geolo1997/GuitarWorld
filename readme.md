# 吉他世界 (Guitar World)
## 项目简介
&emsp;&emsp;该项目为本人独立开发的Android应用，项目旨在为广大吉他爱好者建立一个具有开源精神的吉他交流平台，用户可以在应用上发布或者浏览其他用户发布的教学视频、演奏视频和吉他谱，进行点赞、评论、提问、私信等形式的互动，促进吉他爱好者之间的交流和进步。  
&emsp;&emsp;同时应用还将具有调音器、节拍器、常用和弦等功能。

## 项目进度
&emsp;&emsp;项目从2019年3月份正式进行开发，断断续续至今。  
&emsp;&emsp;应用目前具有注册，登录，修改资料，发布图文作品、视频作品，浏览他人作品，浏览吉他谱，点赞，评论，关注，以歌曲为中心的吉他谱、教学视频、演奏视频分类，吉他资讯等功能。  
&emsp;&emsp;由于本人身单力薄，水平有限，所以进度较慢，还没有达到预期的可上线的水平。正在努力开发中，敬请期待。

## 技术栈
&emsp;&emsp;项目使用原生Android开发，使用Java语言。  
&emsp;&emsp;架构方面采用单Activity + 多Fragment架构，纵向解耦视图控制层，依赖开源库Fragmentation。  
&emsp;&emsp;网络层使用Retrofit + OkHttp，接口遵循RESTful风格，封装了多个回调接口，包括返回数据的回调和文件下载进度的回调。  
&emsp;&emsp;数据库使用LitePal框架，面向接口编程。  
&emsp;&emsp;增加了model层封装网络和数据库操作，Fragment调用model层而不直接操作网络或数据库，屏蔽数据来源细节，增强了可维护性。  
&emsp;&emsp;除此之外使用了众多开源UI库，实现了很多炫酷的视图效果。

## 应用截图
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-111821_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-111827_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-111831_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173949_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173858_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173937_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-112009_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-112034_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173517_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-163253_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173757_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-164927_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-174517_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-173810_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-174713_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-174718_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-174724_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-174733_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-175154_Guitar%20World.jpg"/></span>
<span><img width="290" height="580" src="https://github.com/Geolo1997/GuitarWorld/blob/master/images/Screenshot_20190803-175538_Guitar%20World.jpg"/></span>


## 未来开发计划
1. 完善已有功能，优化数据获取接口和表单验证。
2. 优化视图加载代码，改善使用流畅度，进一步丰富视图效果。
3. 接入微信登录。
4. 使用Dagger或Spring对各层进行依赖注入，替换目前临时使用的“低配版”BeanFactory。
5. 单Activity + 多Fragment虽可以大幅度对视图控制层解耦，但因为Fragment同样是Android的系统组件，并非是仅仅持有布局和控件以及相应操作的普通Java类，所以大量解耦导致一个页面多个Fragment层层嵌套会降低性能，而且因为其view的创建和绑定(attach)过程不受程序员控制，所以难以应用于依赖于适配器(Adapter)创建的各种列表控件(ListView,RecyclerView等)。  
因此打算尝试开发新的ViewController层，只持有控件对象引用和事件监听等操作，提供createView()、getView()、getContext()、 init()等方法，作为轻量级的"Fragment"替代现在Fragment的角色，供Activity、Fragment、Adapter等调用，进一步优化性能，复用代码，解耦视图控制层。

## 版权声明
&emsp;&emsp;本项目为开源项目，开发项目同时也旨在提高编程技术、交流分享，目前并无任何商业目的，应用截图中许多测试内容来自互联网，如有不妥请联系本人及时处理。
