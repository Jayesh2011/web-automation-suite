java -jar selenium-server-standalone-2.53.1.jar -role node -port 5555 -Dwebdriver.chrome.driver=../chromedriver/linux32/chromedriver -browser browserNmae=chrome,maxInstances=5 -browser browserName=firefox,maxInstances=5 -browser browserName=safari -hub http://192.168.21.50:4444/grid/register
pause