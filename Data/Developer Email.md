# Developer Email

Dear * Application Development Team:

​    Hello, we have discovered a security vulnerability that could lead to the leakage of user information to malicious attackers.

​    The security vulnerability is valid for applications within Android mobile devices. The specific threat scenario is that the malware plays a video on the user's device in a small window mode, and it listens for the name of the running foreground application. When it detects that your application is running and it enters a page with sensitive information (e.g. login page, payment page), the malware will pop up a phishing page to overlay on top of the original page and trick the user into entering their information credentials.

​	This process will be shown step-by-step within the recorded screen in Annex I. In order to be able to show more clearly the effect of being overwritten by a phishing page, we set a slight difference between the phishing page and the original page: the position of the control layout is shifted down/a Text text is added. Because of this setting, the interface undergoes a short jump when a phishing attack occurs. In a real threat scenario to the user, this jump would be removed.
​	To address this security vulnerability, we provide you with several suggested security fixes:
(1) When your own application goes into the background, a Toast pops up to alert the user that the application has been switched to the background.
(2) When your application enters the background, a notification panel pops up to alert the user that the application has been switched to the background.
(3) Call startActivity method several times when entering sensitive page, so that you can call your own page again when hijacking occurs to realize anti-hijacking.
​	I hope the suggestions in this email can be adopted.

​                                                                                                                                                                                                                                  *

​                                                                                                                                                                                                                        May 29, 2023