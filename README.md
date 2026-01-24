Paste this exact Markdown into README.md (it fixes your formatting, fixes the broken code block, turns the “requirements” into a real GitHub table, and uses (bash) instead of ```bash). The technical content matches what you implemented in Scenarios 2–3. [file:19]
HTU Schedemy - Production AWS Deployment (DevOps Capstone)

This repository contains the Spring Boot back-end for the HTU “Schedemy” scheduling system and documents how I deployed it on AWS as a production-ready platform (high availability, global access, monitoring/alerts, and safe updates). [file:19]

Important: I do not keep the AWS environment running continuously to avoid charges on a free account; this repo contains the implementation, CI/CD workflow, and screenshots/log evidence from a successful deployment and rollout. [file:19]
Problem Statement

At the beginning of each academic semester at HTU University, the academic administration faces challenges in organizing the process of course assignment for instructors and teaching assistants (TAs). Managing a large number of courses, instructors, and time slots often leads to scheduling conflicts and difficulties in tracking course assignments efficiently.

To address this issue, the university decided to develop an electronic application that helps organize and manage the course registration and scheduling process in a centralized and structured manner. The application is designed around multiple pages, where each page is responsible for a specific task that supports effective management.

The application includes:

    An Add Schedule page that allows adding courses to the semester schedule by specifying lecture time, course, assigned instructor, and teaching assistant.

    A View Schedule page that displays the complete schedule in a clear and organized format.

    A Manage Courses page to view and manage available courses.

    A Manage Instructors page to manage instructor information and their assigned courses.

The development team completed building the application. The back-end is implemented using Spring Boot, and the front-end is hosted in a separate GitHub repository.
What I Built (DevOps Scope)

I designed and implemented an AWS-based deployment that meets production requirements using managed AWS services and DevOps best practices: high availability, auto scaling, global access, monitoring & notifications, and safe updates with minimal downtime. [file:19]
Key Outcomes

    Front-end deployed using AWS Amplify (separate repo). [file:19]

    Back-end deployed on EC2 behind an Application Load Balancer (ALB) with Target Group health checks. [file:19]

    High availability using an Auto Scaling Group (ASG) across multiple AZs. [file:19]

    Global access using CloudFront (front-end) and DNS via Route 53 + TLS via ACM. [file:19]

    Continuous monitoring and notifications using CloudWatch alarms and SNS email notifications. [file:19]

    Safe updates using immutable deployments: artifact-based delivery + controlled rollout (Launch Template versioning + ASG Instance Refresh). [file:19]

Architecture (High Level)

Backend request path (infrastructure layer): [file:19]
GitHub Actions (OIDC) → S3 artifact (releases/app.jar) → ASG Instance Refresh → EC2 (Launch Template user-data + systemd) → Target Group health check (/instructor) → ALB (HTTP) → Users [file:19]

Front-end path (hosting layer): [file:19]
GitHub → Amplify Hosting → CloudFront (CDN) → Route 53 custom domain + ACM TLS [file:19]
Deployment & Infrastructure Requirements (How They Were Met)
Requirement	AWS Services / Implementation
Front-end deployed using Amplify	AWS Amplify Hosting (connected to front-end repo). [file:19]
Back-end on EC2	Spring Boot runs on EC2 and is managed by systemd. [file:19]
Custom domain	Route 53 for DNS, ACM for certificates, CloudFront/Amplify for front-end domain; ALB DNS used for back-end entry. [file:19]
High availability + scaling	Auto Scaling Group + Launch Template + Multi-AZ Target Group behind ALB. [file:19]
Fast global access	CloudFront caches front-end globally; back-end is behind ALB with health checks and scalable EC2 tier. [file:19]
Long-running reliability	CloudWatch alarms + SNS notifications for operational events and abnormal behavior. [file:19]
Notify critical events	SNS subscriptions + ASG notifications (launch/terminate/refresh events). [file:19]
Safe updates without interruption	Immutable artifact deployments + ASG Instance Refresh (rolling replacement with warm-up + health checks). [file:19]
CI/CD (Scenario 3)

I implemented a secure delivery pipeline using GitHub Actions with OIDC authentication to AWS (no stored AWS access keys). [file:19]

Workflow behavior:

    Build the application (Java 17 + Maven). [file:19]

    Upload the JAR artifact to S3: s3://<bucket>/releases/app.jar. [file:19]

    Trigger an Auto Scaling Group Instance Refresh for rolling updates. [file:19]

How To Run Locally (Backend Only)

(bash)
mvn clean package
java -jar target/*.jar
curl http://localhost:8080/instructor
Evidence (Screenshots / Logs)

Because the AWS environment is not always running, I keep proof of successful deployment and rollout under: [file:19]

    docs/screenshots/ (OIDC provider, IAM policy, S3 artifact upload, ASG instance refresh, Target Group healthy targets, ALB endpoint response). [file:19]

Notes / Cost Control

AWS resources were deployed, validated, and then torn down to avoid ongoing charges. The implementation and CI/CD are reproducible from this repo and the documented configuration. [file:19]
Author

Ayoub Alkhalayleh — DevOps Track Capstone (Scenarios 1–3). [file:19]
