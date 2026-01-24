Proof (Screenshots)

This repo does not keep AWS resources running continuously (cost control). The screenshots below prove the AWS deployment and CI/CD rollout were executed successfully.
1) Security boundary (not public)

    ALB Security Group: allows inbound 80/443 (internet-facing)
    01-sg-alb.jpg

    App Security Group: allows inbound 8080 only from the ALB Security Group
    02-sg-app.jpg

2) Artifact-based deployments

    S3 artifact location (releases/app.jar)
    03-s3.jpg

    EC2 instance role / instance profile (read access to artifact bucket)
    04-role.jpg

3) Compute + scaling

    Launch Template used by ASG
    05-LT.jpg

    Launch Template user-data bootstrap (install Java, download artifact, systemd)
    05-LT-UD.jpg

    Auto Scaling Group configuration (capacity / instance management)
    08-asg.jpg

4) Load balancing + health checks

    Target Group configuration + health check path
    06-tg.jpg

    ALB listener forwarding to the Target Group
    07-alb.jpg

    Registered targets healthy
    09-tg.jpg

5) CI/CD (GitHub Actions + OIDC) + safe rollout

    OIDC provider (no long-lived AWS keys in GitHub)
    12-OIDC.jpg

    Least-privilege policy for the workflow (S3 upload + ASG refresh)
    11-github-actions-policy.jpg

    Workflow file stored in the repo
    14-gh-workflow.jpg

    GitHub Actions run showing upload + refresh trigger
    13-gh-actions.jpg

    S3 updated after GitHub Actions upload
    14-s3-after-gh-actions.jpg

    ASG Instance Refresh in progress
    15-gh-actions-instance-refresh.jpg

    Target Group healthy after refresh
    16-gh-actions-tg.jpg

6) Functional validation

    API response via ALB endpoint
    10-site.jpg

