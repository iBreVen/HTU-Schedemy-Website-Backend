# HTU Schedemy - Production AWS Deployment (DevOps Capstone)

This repository contains the Spring Boot back-end for the HTU “Schedemy” scheduling system and documents how I deployed it on AWS as a production-ready platform (high availability, global access, monitoring/alerts, and safe updates).

Important: I do not keep the AWS environment running continuously to avoid charges on a free account; this repo contains the implementation, CI/CD workflow, and screenshots/log evidence from a successful deployment and rollout.

## Problem Statement

At the beginning of each academic semester at HTU University, the academic administration faces challenges in organizing the process of course assignment for instructors and teaching assistants (TAs). Managing a large number of courses, instructors, and time slots often leads to scheduling conflicts and difficulties in tracking course assignments efficiently.

To address this issue, the university decided to develop an electronic application that helps organize and manage the course registration and scheduling process in a centralized and structured manner. The application is designed around multiple pages, where each page is responsible for a specific task that supports effective management.

The application includes:
- Add Schedule page: Add courses by specifying lecture time, course, assigned instructor, and teaching assistant.
- View Schedule page: Display the complete schedule.
- Manage Courses page: View and manage courses.
- Manage Instructors page: Manage instructor info and their assigned courses.

The development team completed building the application. The back-end is implemented using Spring Boot, and the front-end is hosted in a separate GitHub repository.

## What I Built (DevOps Scope)

I designed and implemented an AWS-based deployment that meets production requirements using managed AWS services and DevOps best practices: high availability, auto scaling, global access, monitoring & notifications, and safe updates with minimal downtime.

Key outcomes:
- Front-end deployed using AWS Amplify (separate repo).
- Back-end deployed on EC2 behind an Application Load Balancer (ALB) with Target Group health checks.
- High availability using an Auto Scaling Group (ASG) across multiple AZs.
- Global access using CloudFront (front-end) and DNS via Route 53 + TLS via ACM.
- Continuous monitoring and notifications using CloudWatch alarms and SNS email notifications.
- Safe updates using immutable deployments: artifact-based delivery + controlled rollout (Launch Template versioning + ASG Instance Refresh).

## Architecture (High Level)

Backend request path:
- GitHub Actions (OIDC) → S3 artifact (releases/app.jar) → ASG Instance Refresh
- ASG launches EC2 from Launch Template (user-data bootstrap + systemd service)
- Target Group health check: /instructor
- ALB (HTTP) forwards traffic only to healthy targets

Front-end path:
- GitHub → Amplify Hosting → CloudFront (CDN) → Route 53 custom domain + ACM TLS

## Deployment & Infrastructure Requirements (How They Were Met)

| Requirement | AWS Services / Implementation |
| --- | --- |
| Front-end deployed using Amplify | AWS Amplify Hosting (connected to front-end repo). |
| Back-end on EC2 | Spring Boot runs on EC2 and is managed by systemd. |
| Custom domain | Route 53 for DNS, ACM for certificates, CloudFront/Amplify for front-end domain; ALB DNS used for back-end entry. |
| High availability + scaling | Auto Scaling Group + Launch Template + Multi-AZ Target Group behind ALB. |
| Fast global access | CloudFront caches front-end globally; back-end is behind ALB with health checks and scalable EC2 tier. |
| Long-running reliability | CloudWatch alarms + SNS notifications for operational events and abnormal behavior. |
| Notify critical events | SNS subscriptions + ASG notifications (launch/terminate/refresh events). |
| Safe updates without interruption | Immutable artifact deployments + ASG Instance Refresh (rolling replacement with warm-up + health checks). |

## CI/CD (Scenario 3)

I implemented a secure delivery pipeline using GitHub Actions with OIDC authentication to AWS (no stored AWS access keys).

Workflow behavior:
- Build the application (Java 17 + Maven).
- Upload the JAR artifact to S3: s3://<bucket>/releases/app.jar
- Trigger an Auto Scaling Group Instance Refresh for rolling updates.

## How To Run Locally (Backend Only)

    mvn clean package
    java -jar target/*.jar
    curl http://localhost:8080/instructor

## Evidence (Screenshots / Logs)

Because the AWS environment is not always running, I keep proof of successful deployment and rollout under:
- docs/screenshots/ (OIDC provider, IAM policy, S3 artifact upload, ASG instance refresh, Target Group healthy targets, ALB endpoint response)

## Notes / Cost Control

AWS resources were deployed, validated, and then torn down to avoid ongoing charges. The implementation and CI/CD are reproducible from this repo and the documented configuration.

## Author

Ayoub Alkhalayleh — DevOps Track Capstone (Scenarios 1–3).
