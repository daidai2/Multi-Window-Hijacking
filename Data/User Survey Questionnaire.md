# User Survey Questionnaire

## 1 Picture-In-Picture mode

​	Using the picture-in-picture mode in the multi-view scheme, we implemented three attacks. Picture-in-picture is a feature provided by Android to video software that allows the video to play in a small window, enabling the user to continue watching the video while using other applications.

### (1) Picture-in-Picture Attack 1: Picture-in-Picture Hijacking Attack

​    The purpose of this attack is to use the malicious video prepared by the attacker to hijack the small window where the video is playing, so that the original video being played is replaced.
​    For example, when a user is watching a video in a small window to learn the safe operation of a product, the malicious attacker can replace the original safe operation video with a video designed for wrong operation.

​	*there is FS2PIP.mp4.*

​	In this video, the video played by "Aiqiyi" runs in a small window. Then, when the user uses malware disguised as a normal application (in this demo video, the malware is disguised as a novel reader), the malware replaces the running video with a video designed by the attacker in advance.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think picture-in-picture hijacking attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

### (2) Picture-in-Picture Attack 2: Mode Forced Conversion Attack

​    The purpose of this attack is to pop up a phishing page above the attacked software. The phishing page is identical to the page of the attacked software and is used to trick users into actively entering their account password/payment code. In this attack, the malware disguises itself as a normal video software and runs in a small window in picture-in-picture mode, launching the attack when it finds the target to be attacked running.
​    For example, it attacks the login and payment interfaces of Alipay, and the login and payment interfaces of banking software. The malware demonstrated in the following video uses Alipay as the target.

​	*there is PIP2FS@SaS.mp4.*

​	In this video, the malware disguises itself as video software and plays "The Newsroom" in a small window. When the software the user is using is not the target of the malware, the malware will continue to play the video normally, such as the "Bank of Communications" opened in the video, which did not trigger the malware attack. When the user opens "Alipay", the malware will overlay the phishing page on top of the original Alipay login interface, as shown in the video 21-25 seconds.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think mode forced conversion attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

### (3) Picture-in-Picture Attack 3: Task Move Attack

​	The purpose of this attack is the same as Picture-in-Picture Attack 2. The malware is also disguised as a video software, and the malware initiates the attack when the target to be attacked is found running.
​    The malware demonstrated in the video uses Alipay as the target of the attack.

​	*there is PIP2FS@RaR.mp4.*

​	In this video, the malware disguises itself as video software and plays "Old Friends" in a small window. When the user opens Alipay, the malware overlays the phishing page on top of the original Alipay login screen, as shown in seconds 13-16 of the video.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think task move attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

## 2 FreeForm

​	Using the free window mode in the multi-view scheme, we implemented three attacks. Free windowing is a feature provided by Android to manufacturers of devices (phones, tablets, etc.) that allows applications to run in a small windowed mode, allowing the user to operate two applications on the screen at the same time.

### (1) FreeForm Attack 1: Web Page Orientation Attack

​	The malware containing this attack will run in free window mode. When the target to be attacked is found open, it will directly pop up the browser and direct it to a phishing page created by the attacker in advance. The phishing page will be designed to resemble the target's web-side page to trick the user into entering account passwords/payment codes. Additionally, the attack will also display an alert pop-up with the logo of the target to be attacked, which will be used to deceive the user.

​	*there is FF2FS@WT.mp4.*

​	In this video, the malware runs in a small window disguised as pixel graffiti software. When the user is using software that is not our target, the malware will continue to run normally, such as the "Bank of Communications" opened in the video, which did not trigger the malware attack. When the user opens "Alipay", the malware will open the browser, direct the webpage to the phishing link set by the attacker, and pop up a window to tell the user to log in to "Alipay" using the webpage, as shown in the video from 12 to 17 seconds.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think web page orientation attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

### (2) FreeForm Attack 2: Conversion Hijacking Attack

​	The application containing this attack will masquerade as a browser application and run in free window mode. When the target of the attack is found open, the malware will open the browser in a full-screen interface and direct the URL to the web page originally viewed on the malware. In addition, the malware's display will jump to a phishing page with a pop-up message telling the user to "For security reasons, in multi-window scenarios, please use a small window to login/pay".
​    The malware demonstrated in the video below targets Alipay.

​	*there is FF2FS@PR.mp4.*

​	In this video, the malware runs in a small window disguised as browser software. When the user is using software that is not our target, the malware will continue to run normally, such as "Bank of Communications" opened in the video, which does not trigger the malware attack. When the user opens "Alipay", the malware opens the browser and directs the webpage to the page that the malware is browsing (in this demo video, the page is the Android developer guide page). When the malware jumps to the phishing page, a pop-up window will appear to tell the user to log in to Alipay using a small window, as shown in seconds 16-21 of the video.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think conversion hijacking attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

### (3) FreeForm Attack 3: FreeForm Hijacking Attack

​	This attack targets the hijacking of applications running in small windows, and the malware will run in full screen. When the target is found to be open, the malware will jump to a phishing page and pop up a prompt to tell the user "To provide a secure environment, please log in using the login screen on the main screen".
​	The malware demonstrated in the video below targets Alipay.

​	*there is FS2FF.mp4.*

​	In this video, the malware runs on the home screen disguised as an Arxiv paper reader. When the user opens Alipay, the malware jumps to a phishing page and pops up a window telling the user to use the home screen to log into Alipay, as shown in the video from 9 to 14 seconds.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think freeform hijacking attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

## 3 Split-Screen

​	Using the split-screen mode in the multi-view scheme, we implemented a hijacking attack. Split-screen mode is a feature provided by Android to manufacturers of devices (phones, tablets, etc.) that allows two apps to occupy half of the screen each, allowing the user to operate two apps at the same time.

### (1) Split-Screen Attack 1: Force Close Split-Screen Attack

​    This attack aims to attack the application running in the other half of the screen. When the target is found to be open, the malware will force the user to exit the split-screen mode, enter the full-screen mode, and jump to the phishing page. In addition, a pop-up window will appear telling the user to "please log in using the full-screen app".
​    The malware demonstrated in the video below targets Alipay.

​	*there is SS2FS.mp4.*

​	In this video, the malware is disguised as an exchange rate calculator running on one of the split-screen screens. When the user opens "Alipay", the malware will force the user to exit the split-screen mode, jump to a phishing page, and pop up a window telling the user that "for security reasons, please use the main screen to log in to Alipay", as shown in the video from 7 to 12 seconds.

​	**If you are a user with malware, how likely do you think you are to be tricked by that hijack?**

|          1           |     2      |    3     |       4       |    5     |
| :------------------: | :--------: | :------: | :-----------: | :------: |
| Extremely Impossible | Impossible | Possible | Very Possible | Definite |

​	**How harmful do you think force close split-screen attack are in the real world?**

|      1      |      2      |    3    |      4       |         5         |
| :---------: | :---------: | :-----: | :----------: | :---------------: |
| Not Harmful | Low Harmful | Harmful | Very Harmful | Extremely Harmful |

## 4 Multi-window solutions

​	**Do you often use multi-window solutions in mobile devices?**

|        1         |   2    |     3     |     4      |        5        |
| :--------------: | :----: | :-------: | :--------: | :-------------: |
| Extremely Seldom | Seldom | Sometimes | Frequently | Very Frequently |

​	**Do you often use picture-in-picture mode in mobile devices?**

|        1         |   2    |     3     |     4      |        5        |
| :--------------: | :----: | :-------: | :--------: | :-------------: |
| Extremely Seldom | Seldom | Sometimes | Frequently | Very Frequently |

​	**Do you often use freeform mode in mobile devices?**

|        1         |   2    |     3     |     4      |        5        |
| :--------------: | :----: | :-------: | :--------: | :-------------: |
| Extremely Seldom | Seldom | Sometimes | Frequently | Very Frequently |

​	**Do you often use split-screen mode in mobile devices?**

|        1         |   2    |     3     |     4      |        5        |
| :--------------: | :----: | :-------: | :--------: | :-------------: |
| Extremely Seldom | Seldom | Sometimes | Frequently | Very Frequently |

​	**Could you please share your feelings about the security of the Android Multi-window solutions? After learning about these attacks, would you still use the features provided by the Android Multi-window solutions? (not mandatory answer)**

