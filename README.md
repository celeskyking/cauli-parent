cauli-parent
============

一个基于junit和selenium的测试框架，包含一些mock的控件..


cauli-test
----------

一个基于JUnit扩展的模块，扩展了JUnit的多线程，参数化(包含普通的excel和txt，以及Bean形式),以及Filter和运行监听器。

cauli-ui
--------
基于selenium和cauli-test扩展的轻量级的自动化测试框架。集合了JQuery定位写法和链式代码风格。


cauli-server
------------
一个copy于webbit的项目，本身想基于此netty框架扩展一个基于堆栈的微型 Java 框架。提供REST入口，目前处于开发阶段...

###cauli-ui使用说明
######一个hello，world程序
        @RunWith(CauliUIRunner.class)
        @Require(Engine.FIREFOX)
        public class TestUI {
            @Test
            public void testBaidu(){
            go("http://www.baidu.com");
            $("#kw1").input("北京");
            $("#su1").click();
            assertThat("测试title",currentPage().getTitle(),containsString("北京"));
            }
        }
这是一个最简单的入门写法，当然cauli-ui支持pageObject模式，也支持各种高级特性。

######JQuery定位方式和selenium定位方式的混用
        find("id->kw1")
        find("#kw1")







