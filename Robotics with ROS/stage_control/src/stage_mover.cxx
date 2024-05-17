

#include <ros/ros.h>
#include <std_msgs/String.h>

#include <iostream>

#include <ros/ros.h>
#include <geometry_msgs/Twist.h>
#include <nav_msgs/Odometry.h>
#include <sensor_msgs/LaserScan.h>
#include <tf/tf.h>
#include <math.h>
#include <sstream>
using namespace std;
#define PI 3.1415926535
bool is_reached = 0;
bool isClear = 1;
double x_goal,y_goal,yaw_goal;
double x,y;
sensor_msgs::LaserScan scaninfo;
ros::Publisher cmd_vel_pub_;
geometry_msgs::Twist base_cmd;

double roll, pitch, yaw;
void poseCallback(const nav_msgs::Odometry::ConstPtr& msg)
{
	x = msg->pose.pose.position.x;
    y = msg->pose.pose.position.y;
	tf::Quaternion quat;
    tf::quaternionMsgToTF(msg->pose.pose.orientation, quat);
	
    // the tf::Quaternion has a method to acess roll pitch and yaw
    
    tf::Matrix3x3(quat).getRPY(roll, pitch, yaw);
	//cout<<yaw<<endl;
}
void scanCallback(const sensor_msgs::LaserScan::ConstPtr & msg)
{
  	scaninfo = *msg;
}
void move()
{
	double heading_to_goal = atan2(y_goal-y, x_goal-x);
	ros::Rate loop_rate(20);
	cout<<"turning  "<<endl;
	while (abs(yaw - heading_to_goal) > 0.02)
	{	
		base_cmd.angular.z = 0.4;
		cmd_vel_pub_.publish(base_cmd);
		ros::spinOnce();
		loop_rate.sleep();
	}
	base_cmd.angular.z = 0;
	cmd_vel_pub_.publish(base_cmd);
	cout<<"forward "<<endl;
	while (scaninfo.ranges[540] > 1)
	{
		base_cmd.linear.x = 1.5;   
		cmd_vel_pub_.publish(base_cmd);
		ros::spinOnce();
		loop_rate.sleep();
		double min = 1000;
		for (int i =0;i<1080;i++)
		{
			if (scaninfo.ranges[i]<min)
			{
				min = scaninfo.ranges[i];
			}
		}
		cout<<scaninfo.ranges[540]<<"  "<<min<<endl;
		if (min<=0.8)
			break;
		if (abs(x - x_goal) < 0.5 && abs(y - y_goal) < 0.5)
		{
			is_reached = 1;
			break;
		}
	}
	base_cmd.linear.x = 0;   
	cmd_vel_pub_.publish(base_cmd);
}
void wall_following()
{   
	cout<<"parallel to wall"<<endl;
	ros::Rate loop_rate(20);
	double min = 1000;
	for (int i =0;i<1080;i++)
	{
		if (scaninfo.ranges[i]<min)
		{
			min = scaninfo.ranges[i];
		}
	}
	int min_ind =1080;
	for(int i=0;i<1080;i++)
	{
		if(scaninfo.ranges[i]==min)
		{
			min_ind = i;
			break;
		}
	}
	double dtheta = scaninfo.angle_increment * (min_ind - 540) + PI / 2;
	double t0 = ros::Time::now().toSec();
	double rotated_angle = 0;
	while (rotated_angle < dtheta)
	{       
		base_cmd.angular.z = 0.2;
		cmd_vel_pub_.publish(base_cmd);
		double t1 = ros::Time::now().toSec();
		rotated_angle = (t1-t0) * base_cmd.angular.z; 
		ros::spinOnce();
		loop_rate.sleep();
	}
	base_cmd.angular.z = 0;
	cmd_vel_pub_.publish(base_cmd);
	cout<<"following the wall"<<endl;
	double right_dis = scaninfo.ranges[180];
	
	while (scaninfo.ranges[540] > 0.9)
	{
		base_cmd.linear.x = 1.5;   
		cmd_vel_pub_.publish(base_cmd);
		ros::spinOnce();
		loop_rate.sleep();
		if (abs(right_dis - scaninfo.ranges[180]) > 1 || 3 < scaninfo.ranges[180])
		{
			double angle_rot= 0;
			base_cmd.angular.z = -0.6;
			double t10 = ros::Time::now().toSec();
			while (abs(angle_rot) < PI / 3.6 && scaninfo.ranges[540] > 0.9)
			{
				cmd_vel_pub_.publish(base_cmd);
				ros::spinOnce();
				loop_rate.sleep();
				double t11 = ros::Time::now().toSec();
				angle_rot = (t11-t10) * base_cmd.angular.z;
			}
			double dist_trav = 0;
			double t00 = ros::Time::now().toSec();
			while (dist_trav < 2 && scaninfo.ranges[540] > 0.9)
			{
				cmd_vel_pub_.publish(base_cmd);
				ros::spinOnce();
				loop_rate.sleep();
				double t01 = ros::Time::now().toSec();
				dist_trav = (t01-t00) * base_cmd.linear.x;
			}
			break;
		}
	}
	base_cmd.angular.z = 0;
	base_cmd.linear.x = 0;
	cmd_vel_pub_.publish(base_cmd);

	double angle_diff = yaw - atan2(y_goal-y, x_goal-x);
	int diff_ind = int(540) - int(angle_diff / scaninfo.angle_increment);
	if (abs(angle_diff) < 3 * PI / 4 && scaninfo.ranges[diff_ind] > 3)
		isClear = 1;
	else
		isClear = 0;
}
void final_orientation()
{   
	cout<<"towards final orientation"<<endl;
	ros::Rate loop_rate(20);
	while (abs(yaw - yaw_goal) > 0.02)
	{
		base_cmd.angular.z = 0.4;
		cmd_vel_pub_.publish(base_cmd);
		ros::spinOnce();
		loop_rate.sleep();
	}
	cout<<"Finish"<<endl;
}
int main(int argc, char **argv)
{
	/// Name your node
	ros::init(argc, argv, "bug0_alg");
	ros::NodeHandle nh_;
	cmd_vel_pub_ = nh_.advertise<geometry_msgs::Twist>("/cmd_vel", 1);
	ros::Subscriber odom_sub = nh_.subscribe("/base_pose_ground_truth", 1000, poseCallback);
    ros::Subscriber scan_sub = nh_.subscribe("/base_scan", 1000, scanCallback);

    sscanf(argv[1],"%lf",&x_goal);
	sscanf(argv[2],"%lf",&y_goal);
	sscanf(argv[3],"%lf",&yaw_goal);

	cout<<"GOAL: "<<x_goal<<" "<<y_goal<<" "<<yaw_goal<<" "<<endl;
	
	ros::Duration(2).sleep();
	ros::Rate loop_rate(20);
	ros::spinOnce();
	while (ros::ok())
	{
		if (isClear)
		 	move();
        if (is_reached)
		{
			final_orientation();
            break;
		}
        wall_following();
		/// Spin the ros main loop once 
		
		/// Sleep for as long as needed to achieve the loop rate.
		loop_rate.sleep();
		ros::spinOnce();
	}
	return 0;
}

