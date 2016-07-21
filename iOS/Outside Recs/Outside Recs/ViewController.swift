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

class ViewController: UIViewController {

    @IBOutlet var backgroundVideo: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        loadVideo()
        // Do any additional setup after loading the view, typically from a nib.
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
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ViewController.loopVideoWhenDone(_:)), name: "AVPlayerItemDidPlayToEndTimeNotification", object: videoPlayer.currentItem)
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

