'''
#完成点击弹出键盘的自动化脚本（以交通银行、建设银行为例）
author:wpj
date:2022.2
'''
import uiautomator2 as u2
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

#打开被测试的APP（测试密码输入等对安全性有要求的软件，以中国建设银行和交通银行为例）
#单击直到元素消失，超时时间10，点击间隔1
#device(text='中国建设银行').click_gone(maxretry=10,interval=1.0)
device(text='交通银行').click_gone(maxretry=10,interval=1.0)

#等待元素出现（开屏广告）
device(textContains='转账').wait(timeout=10.0)
#点击进入转账汇款界面（需要密码输入）
if device(textContains='转账').exists:
    device(textContains='转账').click()

device(textContains='输入').wait(timeout=3.0)
#打开输入密码的信息，有“请输入登录密码”的提示语
if device(textContains='输入').exists:
    device(textContains='输入').click()
#如果没有“请输入登录密码”的提示语，则用两种class的情况分别尝试点击弹出键盘
else:
    if device(className="android.widget.ScrollView").exists:
        device(className="android.widget.ScrollView").click()
    else:
        if device(className="android.widget.EditText").exists:
            device(className="android.widget.EditText").click()
