# Proof (Screenshots)

This repo does not keep AWS resources running continuously (cost control). The screenshots below prove the AWS deployment and CI/CD rollout were executed successfully.

## 1) Security boundary (not public)

### ALB Security Group: inbound 80/443 (internet-facing)
![ALB Security Group - inbound 80/443](./screenshots/HERE/01-sg-alb.jpg)

### App Security Group: inbound 8080 only from the ALB Security Group
![App Security Group - 8080 only from ALB SG](./screenshots/HERE/02-sg-app.jpg)

## 2) Artifact-based deployments

### S3 artifact location (releases/app.jar)
![S3 artifact location](./screenshots/HERE/03-s3.jpg)

### EC2 instance role / instance profile (read access to artifact bucket)
![EC2 instance role](./screenshots/HERE/04-role.jpg)

## 3) Compute + scaling

### Launch Template used by ASG
![Launch Template](./screenshots/HERE/05-LT.jpg)

### Launch Template user-data bootstrap (install Java, download artifact, systemd)
![Launch Template user-data](./screenshots/HERE/05-LT-UD.jpg)

### Auto Scaling Group configuration (capacity / instance management)
![Auto Scaling Group](./screenshots/HERE/08-asg.jpg)

## 4) Load balancing + health checks

### Target Group configuration + health check path
![Target Group configuration](./screenshots/HERE/06-tg.jpg)

### ALB listener forwarding to the Target Group
![ALB listener forwarding](./screenshots/HERE/07-alb.jpg)

### Registered targets healthy
![Registered targets healthy](./screenshots/HERE/09-tg.jpg)

## 5) CI/CD (GitHub Actions + OIDC) + safe rollout

### OIDC provider (no long-lived AWS keys in GitHub)
![OIDC provider](./screenshots/HERE/12-OIDC.jpg)

### Least-privilege policy for the workflow (S3 upload + ASG refresh)
![GitHub Actions IAM policy](./screenshots/HERE/11-github-actions-policy.jpg)

### Workflow file stored in the repo
![Workflow file](./screenshots/HERE/14-gh-workflow.jpg)

### GitHub Actions run showing upload + refresh trigger
![GitHub Actions run](./screenshots/HERE/13-gh-actions.jpg)

### S3 updated after GitHub Actions upload
![S3 updated after pipeline](./screenshots/HERE/14-s3-after-gh-actions.jpg)

### ASG Instance Refresh in progress
![ASG Instance Refresh in progress](./screenshots/HERE/15-gh-actions-instance-refresh.jpg)

### Target Group healthy after refresh
![Target Group healthy after refresh](./screenshots/HERE/16-gh-actions-tg.jpg)

## 6) Functional validation

### API response via ALB endpoint
![API response via ALB endpoint](./screenshots/HERE/10-site.jpg)
