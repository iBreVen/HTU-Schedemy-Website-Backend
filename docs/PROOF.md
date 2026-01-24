# Proof (Screenshots)

This repo does not keep AWS resources running continuously (cost control). The screenshots below prove the AWS deployment and CI/CD rollout were executed successfully.

## 1) Security boundary (not public)

### ALB Security Group: inbound 80/443 (internet-facing)
![ALB Security Group - inbound 80/443](./screenshots/01-sg-alb.png)

### App Security Group: inbound 8080 only from the ALB Security Group
![App Security Group - 8080 only from ALB SG](./screenshots/02-sg-app.png)

## 2) Artifact-based deployments

### S3 artifact location (releases/app.jar)
![S3 artifact location](./screenshots/03-s3.png)

### EC2 instance role / instance profile (read access to artifact bucket)
![EC2 instance role](./screenshots/04-role.png)

## 3) Compute + scaling

### Launch Template used by ASG
![Launch Template](./screenshots/05-LT.png)

### Launch Template user-data bootstrap (install Java, download artifact, systemd)
![Launch Template user-data](./screenshots/05-LT-UD.png)

### Auto Scaling Group configuration (capacity / instance management)
![Auto Scaling Group](./screenshots/08-asg.png)

## 4) Load balancing + health checks

### Target Group configuration + health check path
![Target Group configuration](./screenshots/06-tg.png)

### ALB listener forwarding to the Target Group
![ALB listener forwarding](./screenshots/07-alb.png)

### Registered targets healthy
![Registered targets healthy](./screenshots/09-tg.png)

## 5) CI/CD (GitHub Actions + OIDC) + safe rollout

### OIDC provider (no long-lived AWS keys in GitHub)
![OIDC provider](./screenshots/12-OIDC.png)

### Least-privilege policy for the workflow (S3 upload + ASG refresh)
![GitHub Actions IAM policy](./screenshots/11-github-actions-policy.png)

### Workflow file stored in the repo
![Workflow file](./screenshots/14-gh-workflow.png)

### GitHub Actions run showing upload + refresh trigger
![GitHub Actions run](./screenshots/13-gh-actions.png)

### S3 updated after GitHub Actions upload
![S3 updated after pipeline](./screenshots/14-s3-after-gh-actions.png)

### ASG Instance Refresh in progress
![ASG Instance Refresh in progress](./screenshots/15-gh-actions-instance-refresh.png)

### Target Group healthy after refresh
![Target Group healthy after refresh](./screenshots/16-gh-actions-tg.png)

## 6) Functional validation

### API response via ALB endpoint
![API response via ALB endpoint](./screenshots/10-site.png)
