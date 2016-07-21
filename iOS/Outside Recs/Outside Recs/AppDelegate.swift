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

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override point for customization after application launch.
        SPTAuth.defaultInstance().clientID = "6153f36c8e9b426a84d999559435ec8e"
        SPTAuth.defaultInstance().redirectURL = NSURL(string: "outside-recs://returnAfterLogin")
        
        var loginURL : NSURL = SPTAuth.defaultInstance().loginURL
        
        application.performSelector(#selector(self.openURL), withObject: loginURL, afterDelay: 0.1)
        return true
    }

    func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject) -> Bool {
        // Ask SPTAuth if the URL given is a Spotify authentication callback
        if SPTAuth.defaultInstance().canHandleURL(url) {
            SPTAuth.defaultInstance().handleAuthCallbackWithTriggeredAuthURL(url, callback: {(error: NSError, session: SPTSession) -> Void in
                if error != nil {
                    print("*** Auth error: \(error!)")
                    return
                }
                // Call the -playUsingSession: method to play a track
                self.playUsingSession(session)
            })
            return true
        }
        return false
    }
    
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

