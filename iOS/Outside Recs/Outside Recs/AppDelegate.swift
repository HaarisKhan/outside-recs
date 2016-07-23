//
//  AppDelegate.swift
//  Outside Recs
//
//  Created by Alex Kazorian on 7/18/16.
//  Copyright © 2016 AlexK. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    let kClientID = "6153f36c8e9b426a84d999559435ec8e"
    let kCallbackURI = NSURL(string: "test://callback")

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override pointfor customization after application launch
        UIApplication.sharedApplication().statusBarStyle = .LightContent
        return true
    }

    /* func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject?) -> Bool {
        
        if SPTAuth.defaultInstance().canHandleURL(kCallbackURI) {
            SPTAuth.defaultInstance().handleAuthCallbackWithTriggeredAuthURL(url, callback: { (error: NSError!, session: SPTSession!) -> Void in
            
                if error != nil {
                    print("*** Auth error: \(error)")
                    return
                }
                
                print("Some debugging")
                
                let userDefaults = NSUserDefaults.standardUserDefaults()
                let sessionData = NSKeyedArchiver.archivedDataWithRootObject(session)
                
                userDefaults.setObject(sessionData, forKey: "SpotifySession")
                
                NSNotificationCenter.defaultCenter().postNotificationName("loginSuccessfull", object: nil)
                print("loginSuccessfull")
                
            })
        }
        
        return false
        
    } */
    
    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

