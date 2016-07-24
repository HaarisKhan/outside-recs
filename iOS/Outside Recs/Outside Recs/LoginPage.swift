//
//  ViewController.swift
//  Outside Recs
//
//  Created by Alex Kazorian on 7/18/16.
//  Copyright © 2016 AlexK. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation
import Material
import SnapKit

class LoginPage: UIViewController, SPTAuthViewDelegate {
    
    @IBOutlet var backgroundImage: UIImageView!
    @IBOutlet var backgroundVideo: UIView!
    var loginButton: UIButton!
    var SpotifyLogo: UIImageView!
    
    let kClientID = "6153f36c8e9b426a84d999559435ec8e"
    let kCallbackURI = "test://callback"
    let auth = SPTAuth.defaultInstance()
    let user = NSUserDefaults.standardUserDefaults()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        backgroundImage.hidden = true
        loadVideo()
        prepareLoginButton()
        prepareSpotifyLogo()
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    @objc func authenticationViewControllerDidCancelLogin(authenticationViewController: SPTAuthViewController!) {
        
        print("Did press cancel")
        authenticationViewController.clearCookies(nil)
        
    }
    
    @objc func authenticationViewController(authenticationViewController: SPTAuthViewController!, didFailToLogin error: NSError!) {
        
        print(error)
        authenticationViewController.clearCookies(nil)
        
    }
    
    @objc func authenticationViewController(authenticationViewController: SPTAuthViewController!, didLoginWithSession session: SPTSession!) {
        
        print("Did login")
        let sessionData = NSKeyedArchiver.archivedDataWithRootObject(session)
        user.setObject(sessionData, forKey: "Spotify Session")
        self.performSegueWithIdentifier("showUserRecommendations", sender: self)
        
    }
    
    private func prepareLoginButton() {
        
        loginButton = UIButton(frame: CGRect(x: 0, y: 0, width: self.view.frame.width, height: 56))
        loginButton.backgroundColor = MaterialColor.orange.darken1
        loginButton.setTitle("LOG IN", forState: .Normal)
        loginButton.titleLabel!.font = MaterialFont.boldSystemFontWithSize(20)
        loginButton.titleLabel!.textAlignment = .Center
        loginButton.setTitleColor(MaterialColor.white, forState: .Normal)
        loginButton.addTarget(self, action: #selector(LoginPage.didPressLoginButton(_:)), forControlEvents: .TouchUpInside)
        self.view.addSubview(loginButton)
        
        loginButton.snp_makeConstraints { (make) -> Void in
            make.width.equalTo(self.view.frame.width)
            make.height.equalTo(64)
            make.bottomMargin.equalTo(0)
        }
        
    }
    
    private func prepareSpotifyLogo() {
        
        let logo = UIImage(named: "SpotifyButton")
        let SpotifyLogo = UIImageView(image: logo)
        SpotifyLogo.contentMode = .ScaleAspectFit
        self.loginButton.addSubview(SpotifyLogo)
        
        SpotifyLogo.snp_makeConstraints { (make) -> Void in
            make.width.equalTo(self.loginButton.frame.width / 4)
            make.height.equalTo(56)
            make.centerY.equalTo(self.loginButton.snp_centerY)
            make.leftMargin.equalTo(self.loginButton.snp_leftMargin).offset(10)
        }
        
        self.view.addSubview(loginButton)
        
    }
    
    @objc private func didPressLoginButton(sender: UIButton!) {
        
        auth.clientID = kClientID
        auth.redirectURL = NSURL(string: kCallbackURI)
        auth.requestedScopes = [SPTAuthUserFollowReadScope, SPTAuthPlaylistModifyPrivateScope, SPTAuthUserReadEmailScope,SPTAuthUserReadPrivateScope, SPTAuthUserReadBirthDateScope, "user-top-read"]
        
        let spotifyAuthViewController = SPTAuthViewController.authenticationViewController()
        spotifyAuthViewController.delegate = self
        spotifyAuthViewController.modalPresentationStyle = UIModalPresentationStyle.OverCurrentContext
        spotifyAuthViewController.modalTransitionStyle = UIModalTransitionStyle.CrossDissolve
        self.modalPresentationStyle = UIModalPresentationStyle.CurrentContext
        spotifyAuthViewController.definesPresentationContext = true
        presentViewController(spotifyAuthViewController, animated: false, completion: nil)
        
        //var loginURL : NSURL = auth.loginURL
        //UIApplication.sharedApplication().openURL(loginURL)
        
    }
    

    private func loadVideo() {
        let videoURL = NSURL(fileURLWithPath: NSBundle.mainBundle().pathForResource("OutsideRecsiPhone", ofType: "mp4")!)
        let videoPlayer = AVPlayer(URL: videoURL)
        let videoLayer = AVPlayerLayer(player: videoPlayer)
        
        videoLayer.frame = self.backgroundVideo.frame
        self.backgroundVideo.layer.addSublayer(videoLayer)
        videoLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
        
        videoPlayer.play()
        videoPlayer.actionAtItemEnd = AVPlayerActionAtItemEnd.None
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(LoginPage.loopVideoWhenDone(_:)), name: "AVPlayerItemDidPlayToEndTimeNotification", object: videoPlayer.currentItem)
    }
    
    func loopVideoWhenDone(notification: NSNotification) {
        let videoPlayer: AVPlayerItem = notification.object as! AVPlayerItem
        videoPlayer.seekToTime(kCMTimeZero)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
}

