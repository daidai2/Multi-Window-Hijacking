'''
#完成通过系统截屏的自动化脚本
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

#下拉打开手机顶部菜单工具栏
device.swipe(800,0,800,500)

#展开快捷菜单栏
device(description="展开快捷设置").click()

#点击截屏按键
device(description='截屏').click()
device(text='截屏').down(clickable='true').click()
