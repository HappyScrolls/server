# HappyScrolls

## 📝 Writing
#### [1차 개발 후기](https://velog.io/@chs98412/1%EC%B0%A8-%EA%B0%9C%EB%B0%9C-%ED%9B%84%EA%B8%B0)

<div align="center">
  <img src="https://github.com/HappyScrolls/server/assets/79582366/6dce8b65-b114-4143-a3be-44d1ee63579a" width="300">
</div>

## 프로젝트 구조
![pzs](https://github.com/HappyScrolls/server/assets/79582366/2c602e63-066d-4798-b33d-8e749a1d4714)
![image](https://github.com/HappyScrolls/server/assets/79582366/af6540cd-463f-4577-92a7-b638554ed121)

github action을 통해 배포 자동화를 구성하였습니다. </br> </br> </br>
aws 오토스케일을 통해 ec2 인스턴스의 cpu사용량이 증가하면 ec2 인스턴스가 추가되고, 도커로 컨테이너를 기반으로 스프링부트 어플리케이션이 가동됩니다. </br>
이렇게 생성된 여러개의 어플리케이션은 elb를 통해 로드밸런싱 됩니다. </br> </br>
레디스 서버를 통해 캐싱을 진행합니다. </br> </br>
RDS의 서브넷을 프라이빗 서브넷으로 두어서 ec2 인스턴스가 아니면 접근할 수 없도록 하였습니다. </br> </br>
테스트 코드를 작성하고 있으며, jacoco를 통해 커러비지를 측정하고 있습니다.



### 진행도
1차 개발 완료, 테스트 배포
